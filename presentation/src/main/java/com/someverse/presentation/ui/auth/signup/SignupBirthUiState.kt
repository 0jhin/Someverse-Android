package com.someverse.presentation.ui.auth.signup

data class SignupBirthUiState(
    val year: Int = 1991,
    val month: Int = 4,
    val day: Int = 4,
    val age: Int = 34,
    val isUnderAge: Boolean = false,
    val showErrorToast: Boolean = false,
    val errorMessage: String = ""
)