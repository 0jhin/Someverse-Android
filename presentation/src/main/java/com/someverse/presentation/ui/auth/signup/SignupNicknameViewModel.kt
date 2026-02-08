package com.someverse.presentation.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.someverse.domain.usecase.onboarding.SubmitNicknameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupNicknameViewModel
@Inject
constructor(
    private val submitNicknameUseCase: SubmitNicknameUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignupNicknameUiState())
    val uiState: StateFlow<SignupNicknameUiState> = _uiState.asStateFlow()

    fun onValueChange(value: String) {
        _uiState.update { it ->
            it.copy(nickname = value)
        }
    }

    fun submitNickname(onSuccess: () -> Unit) {
        viewModelScope.launch {
            submitNicknameUseCase(nickname = _uiState.value.nickname)
                .onSuccess { user ->
                    _uiState.update { it.copy(errorMessage = "") }
                    onSuccess()
                }
                .onFailure { error ->
                    val errorMsg = error.message ?: "에러 발생"
                    _uiState.update { it.copy(errorMessage = errorMsg) }
                }
        }
    }

    fun deleteErrorMessage() {
        _uiState.update { it.copy(errorMessage = "") }
    }
}