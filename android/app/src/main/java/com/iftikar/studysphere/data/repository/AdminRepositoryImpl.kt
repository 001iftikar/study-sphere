package com.iftikar.studysphere.data.repository

import android.util.Log
import com.iftikar.studysphere.data.dto.UserResponseDto
import com.iftikar.studysphere.domain.DataError
import com.iftikar.studysphere.domain.EmptyResult
import com.iftikar.studysphere.domain.Result
import com.iftikar.studysphere.domain.repository.AdminRepository
import com.iftikar.studysphere.domain.toDataError
import com.iftikar.studysphere.shared.InternetConnectivityObserver
import com.iftikar.studysphere.shared.LocalUserSession
import com.iftikar.studysphere.shared.LocalUserSessionHandler
import com.iftikar.studysphere.utils.toEpochMilli
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.Session
import io.appwrite.models.User
import io.appwrite.services.Account
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okio.IOException
import java.time.Instant

class AdminRepositoryImpl @Inject constructor(
    client: Client,
    private val localUserSessionHandler: LocalUserSessionHandler,
    private val internetConnectivityObserver: InternetConnectivityObserver
) : AdminRepository {
    val account = Account(client)
    override suspend fun signUp(
        email: String,
        password: String,
        name: String
    ): EmptyResult<DataError> = withContext(Dispatchers.IO) {
        try {
            // todo-> username check will be done here before creating the user to store in db
            account.create(
                userId = ID.unique(),
                email = email,
                password = password,
                name = name
            )

            account.createEmailPasswordSession(
                email = email, password = password
            )

            Result.Success(Unit)
        } catch (ex: AppwriteException) {
            Log.e("Appwrite-Repo-Create-Error", "signUp: ${ex.message}")
            Result.Error(ex.toDataError())
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<User<UserResponseDto>, DataError> = withContext(Dispatchers.IO) {
        try {
            val session = account.createEmailPasswordSession(
                email = email,
                password = password
            )

            val user = account.get(nestedType = UserResponseDto::class.java)
            localUserSessionHandler.setLocalUserSession(
                isVerified = user.emailVerification,
                name = user.name,
                expire = session.expire.toEpochMilli()
            )
            Result.Success(user)

        } catch (ex: AppwriteException) {
            Log.e("Appwrite-Repo-Login-Error", "signUp: ${ex.message}")
            Result.Error(DataError.Remote.UNKNOWN)
        } catch (ex: IOException) {
            Result.Error(DataError.Remote.NO_INTERNET)
        } catch (ex: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun sendEmailVerification(url: String): EmptyResult<DataError> =
        withContext(Dispatchers.IO) {
            try {
                account.createEmailVerification(url)
                Result.Success(Unit)
            } catch (ex: AppwriteException) {
                Result.Error(ex.toDataError())
            } catch (ex: IOException) {
                Result.Error(DataError.Remote.NO_INTERNET)
            } catch (ex: Exception) {
                Result.Error(DataError.Remote.UNKNOWN)
            }
        }

    override suspend fun verifyEmail(userId: String, secret: String): EmptyResult<DataError> =
        withContext(Dispatchers.IO) {
            try {
                val token = account.updateEmailVerification(
                    userId = userId,
                    secret = secret
                )

                Log.d("Appwrite-Repo-Verify2", "verifyEmail: ${token.userId}")
                Result.Success(Unit)
            } catch (ex: AppwriteException) {
                Log.e("Appwrite-Repo-Verify2-Error", "signUp: ${ex.message}")
                Result.Error(ex.toDataError())
            }
        }

    override suspend fun checkAuthSession(): Result<com.iftikar.studysphere.domain.model.Session, DataError> =
        withContext(Dispatchers.IO) {
            try {
                val isConnected = internetConnectivityObserver.isConnected().first()
                if (isConnected) {
                    val session = checkSessionWhenOnline()
                    val user = account.get()
                    val isVerified = user.emailVerification
                    val name = user.name
                    val localSession = localUserSessionHandler.readLocalUserSession().first()
                    if (session.expire.toEpochMilli() != localSession.expire) {
                        localUserSessionHandler.setLocalUserSession(
                            isVerified = isVerified,
                            name = name,
                            expire = session.expire.toEpochMilli()
                        )
                    }
                    Result.Success(
                        com.iftikar.studysphere.domain.model.Session(
                            userName = name,
                            isVerified = isVerified
                        )
                    )
                } else {
                    val localAuthSession = checkSessionWhenOffline()
                    Result.Success(
                        com.iftikar.studysphere.domain.model.Session(
                            userName =
                                localAuthSession.name,
                            isVerified = localAuthSession.isVerified
                        )
                    )
                }
            } catch (ex: Exception) {
                Log.e("Appwrite-Session", "checkAuthSession: ${ex.message}")
                Result.Error(DataError.Remote.AUTH_FAILED)
            }
        }

    // when internet available call this fun
    private suspend fun checkSessionWhenOnline(): Session {
        Log.d("Appwrite-Sess-On", "checkSessionWhenOnline: Device online")
        return try {
            account.getSession("current")
        } catch (ex: AppwriteException) { // if it does not throw any exception, that means session exists, so no need to return a nullable session
            throw ex
        }
    }

    // when offline call this fun
    private suspend fun checkSessionWhenOffline(): LocalUserSession {
        Log.d("Appwrite-Sess-Off", "checkSessionWhenOnline: Device offline")
        val localSession = localUserSessionHandler.readLocalUserSession().first()
        if (localSession.expire < Instant.now().toEpochMilli()) {
            throw Exception("Not authenticated")
        }
        return localSession
    }
}





















