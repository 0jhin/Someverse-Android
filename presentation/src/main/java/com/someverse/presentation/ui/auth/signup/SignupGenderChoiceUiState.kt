package com.someverse.presentation.ui.auth.signup

enum class Gender {
    MEN, WOMEN, NONE
}

data class SignupGenderChoiceUiState(
    val gender: Gender = Gender.NONE
)