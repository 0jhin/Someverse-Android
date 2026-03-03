package com.someverse.presentation.navigation

/**
 * Navigation Screens
 * - Defines all navigation routes in the app
 */
sealed class Screen(
    val route: String,
) {
    // Splash
    data object Splash : Screen("splash")

    // Auth
    data object Login : Screen("login")

    // Onboarding Screens
    data object SignupNickname : Screen("signup_nickname")
    data object SignupGenderChoice : Screen("signup_gender_choice")

    data object SignupBirth : Screen("signup_birth")

    data object SignupLocation : Screen("signup_location")

    data object SignupProfileImage : Screen("signup_profile_image")

    data object SignupMovieCategory : Screen("signup_movie_category")

    data object SignupMovieTaste : Screen("signup_movie_taste")

    data object SignupComplete : Screen("signup_complete")

    // Main (with BottomNavigation)
    data object Main : Screen("main")

    // Tab Screens
    data object MyProfile : Screen("my_profile")

    data object Feed : Screen("feed")

    data object Matching : Screen("matching")

    data object Chat : Screen("chat")

    // Feed Related Screens
    data object CreateFeed : Screen("create_feed")

    // Chat Related Screens
    data object WaitingRoom : Screen("waiting_room")

    data object DetailChat : Screen("detail_chat/{roomId}") {
        fun createRoute(roomId: Long) = "detail_chat/$roomId"
    }
}
