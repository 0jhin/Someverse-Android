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
        _uiState.update {
            it.copy(nickname = value, isNicknameAvailable = false, errorMessage = "")
        }
    }

    fun validateNickname() {
        val currentNickname = _uiState.value.nickname
        if (currentNickname.isBlank()) return

        viewModelScope.launch {
            submitNicknameUseCase(nickname = currentNickname)
                .onSuccess {
                    _uiState.update { it.copy(isNicknameAvailable = true, errorMessage = "") }
                }
                .onFailure { error ->
                    val errorMsg = error.message ?: "이미 사용 중인 닉네임입니다."
                    _uiState.update {
                        it.copy(
                            isNicknameAvailable = false,
                            errorMessage = errorMsg
                        )
                    }
                }
        }
    }

    fun deleteErrorMessage() {
        _uiState.update { it.copy(errorMessage = "") }
    }
}