package com.iftikar.studysphere.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.studysphere.domain.onError
import com.iftikar.studysphere.domain.onSuccess
import com.iftikar.studysphere.domain.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SessionHandlingViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {

    private val _eventState = MutableStateFlow<SessionHandlingEvent>(SessionHandlingEvent.Idle)
    val event = _eventState.asStateFlow()

    init {
        checkAuthSession()
    }

    private fun checkAuthSession() {
        viewModelScope.launch {
            adminRepository.checkAuthSession().onSuccess { session ->
                _eventState.value = SessionHandlingEvent.OnAuthSuccess(name = session.userName, isVerified = session.isVerified)
            }.onError { error ->
                _eventState.value = SessionHandlingEvent.OnAuthFailed
            }
        }
    }
}

sealed interface SessionHandlingEvent {
    data object Idle : SessionHandlingEvent
    data object OnAuthFailed : SessionHandlingEvent
    data class OnAuthSuccess(val name: String, val isVerified: Boolean) : SessionHandlingEvent
}




























