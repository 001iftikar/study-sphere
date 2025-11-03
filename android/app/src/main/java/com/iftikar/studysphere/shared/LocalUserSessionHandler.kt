package com.iftikar.studysphere.shared

import android.content.Context
import androidx.datastore.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LocalUserSessionHandler(
    private val context: Context
) {
    private val Context.dataStore by dataStore(
        fileName = "local-user-session.json",
        serializer = LocalUserSessionSerializer
    )

    suspend fun setLocalUserSession(isVerified: Boolean, name: String, expire: Long) {
        context.dataStore.updateData {
            it.copy(isVerified = isVerified, name = name, expire = expire)
        }
    }

    fun readLocalUserSession(): Flow<LocalUserSession> {
        return context.dataStore.data.flowOn(Dispatchers.IO)
    }
}