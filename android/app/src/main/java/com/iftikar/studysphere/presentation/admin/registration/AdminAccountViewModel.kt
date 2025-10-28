package com.iftikar.studysphere.presentation.admin.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.studysphere.domain.DataError
import com.iftikar.studysphere.domain.onError
import com.iftikar.studysphere.domain.onSuccess
import com.iftikar.studysphere.domain.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AdminAccountViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AdminAccountState())
    val state = _state.asStateFlow()

    private val _event: Channel<AdminAccountEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun onAction(action: AdminAccountAction) {
        when (action) {
            is AdminAccountAction.OnEmailChange -> {
                _state.update {
                    it.copy(email = action.email)
                }
            }

            is AdminAccountAction.OnFullNameChange -> {
                _state.update {
                    it.copy(fullName = action.fullName)
                }
            }

            is AdminAccountAction.OnPasswordChange -> {
                _state.update {
                    it.copy(password = action.password)
                }
            }

            is AdminAccountAction.OnUsernameChange -> {
                _state.update {
                    it.copy(username = action.username)
                }
            }

            AdminAccountAction.OnPasswordVisibilityChange -> {
                _state.update {
                    it.copy(isPasswordVisible = !it.isPasswordVisible)
                }
            }

            AdminAccountAction.OnSignUp -> signUp()
            AdminAccountAction.OnLogIn -> login()
        }
    }

    private fun signUp() {
        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            adminRepository.signUp(
                email = _state.value.email,
                password = _state.value.password,
                name = _state.value.fullName
            ).onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false,
                        accountVerificationText = "Hello ${_state.value.fullName}, please verify your email to continue"
                    )
                }
                _event.send(AdminAccountEvent.OnSuccessUnverified())
            }.onError { error ->
                _state.update { it.copy(isLoading = false) }
                when (error) {
                    DataError.Remote.REQUEST_TIMEOUT -> {
                        _state.update {
                            it.copy(error = "Request timeout, please try again")
                        }
                    }

                    DataError.Remote.USER_EXISTS -> {
                        _state.update {
                            it.copy(error = "email already exists, please log in")
                        }
                    }

                    DataError.Remote.INVALID_EMAIL -> {
                        _state.update {
                            it.copy(error = "Please enter a valid email")
                        }
                    }

                    DataError.Remote.NO_INTERNET -> {
                        _state.update {
                            it.copy(error = "Please check internet connection")
                        }
                    }

                    DataError.Remote.AUTH_FAILED -> {
                        _state.update {
                            it.copy(error = "Authentication failed, try again")
                        }
                    }

                    DataError.Remote.UNKNOWN -> {
                        _state.update {
                            it.copy(error = "Oops, unexpected error occurred")
                        }
                    }

                    else -> {
                        _state.update {
                            it.copy(error = "Oops, unexpected error occurred")
                        }
                    }
                }
            }
        }
    }

    fun checkAuthSession() {
        viewModelScope.launch {
            adminRepository.checkAuthSession()
        }
    }

    private fun login() {
        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            adminRepository.login(
                email = _state.value.email,
                password = _state.value.password
            ).onSuccess { user ->
                Log.d("Appwrite-VM-login", "login: $user and is ${user.emailVerification}")
                _state.update {
                    it.copy(
                        isLoading = false,
                        email = user.email,
                        fullName = user.name,
                        accountVerificationText = if (user.emailVerification) "Already verified"
                        else "Hello ${user.name}, email is not verified, please verify"
                    )
                }

                if (user.emailVerification) {
                    _event.send(AdminAccountEvent.OnLoginSuccessVerified())
                } else {
                    _event.send(AdminAccountEvent.OnSuccessUnverified())
                }
            }.onError { error ->
                _state.update { it.copy(isLoading = false) }
                when (error) {
                    DataError.Remote.REQUEST_TIMEOUT -> {
                        _state.update {
                            it.copy(error = "Request timeout, please try again")
                        }
                    }

                    DataError.Remote.INVALID_EMAIL -> {
                        _state.update {
                            it.copy(error = "Please enter a valid email")
                        }
                    }

                    DataError.Remote.NO_INTERNET -> {
                        _state.update {
                            it.copy(error = "Please check internet connection")
                        }
                    }

                    DataError.Remote.PASSWORD_MISMATCH -> {
                        _state.update {
                            it.copy(error = "email or password mismatch")
                        }
                    }

                    DataError.Remote.AUTH_FAILED -> {
                        _state.update {
                            it.copy(error = "Authentication failed, try again")
                        }
                    }

                    DataError.Remote.UNKNOWN -> {
                        _state.update {
                            it.copy(error = "Oops, unexpected error occurred")
                        }
                    }

                    else -> {
                        _state.update {
                            it.copy(error = "Oops, unexpected error occurred")
                        }
                    }
                }
            }
        }
    }

    fun sendEmailVerification() {
        setErrorToNull()
        viewModelScope.launch {
            adminRepository.sendEmailVerification("https://001iftikar.github.io/emai-verification/") // this is just a typo
                .onSuccess {
                    _state.update {
                        it.copy(
                            accountVerificationText = "Verification link has been sent to your email",
                            isButtonEnabled = false
                        )
                    }
                }.onError { error ->
                    when (error) {
                        DataError.Remote.REQUEST_TIMEOUT -> {
                            _state.update {
                                it.copy(error = "Verification timeout, try again")
                            }
                        }

                        DataError.Remote.TOO_MANY_REQUESTS -> {
                            _state.update {
                                it.copy(error = "Too many requests, try again after some time")
                            }
                        }

                        DataError.Remote.NO_INTERNET -> {
                            _state.update {
                                it.copy(error = "Internet connection not available")
                            }
                        }

                        else -> {
                            _state.update {
                                it.copy(error = "Some unexpected error occurred")
                            }
                        }
                    }
                }
        }
    }

    fun verifyEmail(userId: String, secret: String) {
        setErrorToNull()
        viewModelScope.launch {
            Log.d("Appwrite-VM-verify", "verifyEmail: $userId and $secret")
            adminRepository.verifyEmail(userId, secret)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            accountVerificationText = "Verification Successful",
                            isButtonEnabled = true,
                            accountVerifyButtonText = "Continue",
                            isVerified = true
                        )
                    }
                }.onError { ex ->
                    _state.update {
                        it.copy(error = "Verification unsuccessful, please try again")
                    }
                }
        }
    }

    private fun setErrorToNull() {
        _state.update {
            it.copy(error = null)
        }
    }
}




























































