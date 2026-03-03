package com.someverse.presentation.ui.auth.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupGenderChoiceViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SignupGenderChoiceUiState())
    val uiState: StateFlow<SignupGenderChoiceUiState> = _uiState.asStateFlow()

    fun selectGender(gender: Gender) {
        _uiState.value = _uiState.value.copy(gender = gender)
    }
}