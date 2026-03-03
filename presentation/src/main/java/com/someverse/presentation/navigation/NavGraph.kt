package com.someverse.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.someverse.presentation.ui.auth.login.LoginScreen
import com.someverse.presentation.ui.auth.signup.SignupBirthScreen
import com.someverse.presentation.ui.auth.signup.SignupDoneScreen
import com.someverse.presentation.ui.auth.signup.SignupGenderChoiceScreen
import com.someverse.presentation.ui.auth.signup.SignupLocationScreen
import com.someverse.presentation.ui.auth.signup.SignupMovieCategoryScreen
import com.someverse.presentation.ui.auth.signup.SignupMovieTasteScreen
import com.someverse.presentation.ui.auth.signup.SignupNicknameScreen
import com.someverse.presentation.ui.auth.signup.SignupProfileImageScreen
import com.someverse.presentation.ui.chat.DetailChatScreen
import com.someverse.presentation.ui.main.MainScreen
import com.someverse.presentation.ui.splash.SplashScreen

/**
 * App Navigation Graph
 * - Defines navigation structure
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.SignupNickname.route, // 스플래시 화면으로 시작
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        // Splash
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    // 스플래시 완료 후 메인 화면으로 이동 (테스트용)
                    // TODO: 실제 배포 시에는 로그인 상태를 확인하여 Login 또는 Main으로 분기
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
            )
        }

        // Login
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    // 로그인 성공 시 닉네임 화면으로 이동 (온보딩 첫 단계)
                    navController.navigate(Screen.SignupLocation.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
            )
        }

        composable(route = Screen.SignupNickname.route) {
            SignupNicknameScreen(
                onNextClick = {
                    println("닉네임 작성 완료 -> 성별 입력 화면으로 이동 시작")
                    navController.navigate(Screen.SignupGenderChoice.route)
                    println("✅ 성별 입력 화면 네비게이션 호출 완료")
                },
            )
        }

        composable(route = Screen.SignupGenderChoice.route) {
            SignupGenderChoiceScreen(
                onNextClick = {
                    println("성별 입력 완료 -> 생년월일 입력 화면으로 이동 시작")
                    navController.navigate(Screen.SignupBirth.route) //TODO: 생년월일 입력 화면으로 변경
                    println("✅ 생년월일 입력 화면 네비게이션 호출 완료")
                },
            )
        }

        composable(route = Screen.SignupBirth.route) {
            SignupBirthScreen(
                onNextClick = {
                    println("생년월일 입력 완료 -> 활동지역 입력 화면으로 이동 시작")
                    navController.navigate(Screen.SignupProfileImage.route) //TODO: 활동지역 입력 화면으로 변경
                    println("✅ 활동지역 입력 화면 네비게이션 호출 완료")
                },
            )
        }

        // 4. Location
        composable(route = Screen.SignupLocation.route) {
            SignupLocationScreen(
                onNext = {
                    println("🛫 위치 선택 완료 -> 프로필 이미지 화면으로 이동 시작")
                    navController.navigate(Screen.SignupProfileImage.route)
                    println("✅ 프로필 이미지 화면 네비게이션 호출 완료")
                },
            )
        }

        // 5. Profile Image
        composable(route = Screen.SignupProfileImage.route) {
            SignupProfileImageScreen(
                onNext = {
                    navController.navigate(Screen.SignupMovieCategory.route)
                },
            )
        }

        // 6. Movie Category
        composable(route = Screen.SignupMovieCategory.route) {
            SignupMovieCategoryScreen(
                onNext = {
                    navController.navigate(Screen.SignupMovieTaste.route)
                },
                onBack = {
                    navController.popBackStack()
                },
            )
        }

        // 7. Movie Taste
        composable(route = Screen.SignupMovieTaste.route) {
            SignupMovieTasteScreen(
                onNext = {
                    // 온보딩 완료 시 승인 완료 화면으로 이동
                    navController.navigate(Screen.SignupComplete.route)
                },
            )
        }

        // 8. Signup Complete
        composable(route = Screen.SignupComplete.route) {
            SignupDoneScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onStartClick = {
                    // 썸버스 시작하기 -> 메인 화면(탭바)으로 이동
                    navController.navigate(Screen.Main.route) {
                        // 온보딩 스택 모두 제거
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
            )
        }

        // Main Screen with Bottom Navigation
        composable(route = Screen.Main.route) {
            MainScreen()
        }

        // Detail Chat Screen
        composable(
            route = Screen.DetailChat.route,
            arguments =
                listOf(
                    navArgument("roomId") { type = NavType.LongType },
                ),
        ) {
            DetailChatScreen(
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}
