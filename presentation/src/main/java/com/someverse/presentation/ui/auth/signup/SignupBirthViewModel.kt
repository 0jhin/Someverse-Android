package com.someverse.presentation.ui.auth.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignupBirthViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SignupBirthUiState())
    val uiState = _uiState.asStateFlow()

    fun updateDate(year: Int, month: Int, day: Int) {
        val calculatedAge = calculateAge(year, month, day)
        val isUnder = calculatedAge < 14

        _uiState.update {
            it.copy(
                year = year,
                month = month,
                day = day,
                age = calculatedAge,
                isUnderAge = isUnder,
                showErrorToast = isUnder
            )
        }
    }

    private fun calculateAge(year: Int, month: Int, day: Int): Int {
        val birthDate = java.time.LocalDate.of(year, month, day)
        val currentDate = java.time.LocalDate.now()
        return java.time.Period.between(birthDate, currentDate).years
    }

    fun deleteErrorMessage() {
        _uiState.update { it.copy(showErrorToast = false) }
    }
}