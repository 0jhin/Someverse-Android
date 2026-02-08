package com.someverse.data.local

import com.someverse.data.model.AuthTokenEntity
import com.someverse.data.model.GenreEntity
import com.someverse.data.model.LocationEntity
import com.someverse.data.model.MovieEntity
import com.someverse.data.model.UserEntity
import com.someverse.data.source.AuthDataSource
import com.someverse.domain.model.Gender
import com.someverse.domain.model.Location
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Auth Local DataSource (Mock Implementation)
 * - Implements AuthDataSource interface
 * - Handles local data storage (in-memory for now)
 * - Can be replaced with SharedPreferences, Room, DataStore later
 * - Simulates data access delays
 */
@Singleton
class AuthLocalDataSource
@Inject
constructor() : AuthDataSource {
    // In-memory storage
    private var isAuthenticatedFlag = false
    private var storedToken: AuthTokenEntity? = null
    private var currentUser: UserEntity? = UserEntity(
        id = "user_10000",
        nickname = "테스트",
        age = 28,
        gender = "MALE",
        activityLocations = listOf(
            LocationEntity("서울특별시", "강남구"),
            LocationEntity("경기도", "성남시")
        ),
        profileImages = listOf(
            "https://picsum.photos/id/1/200/300",
            "https://picsum.photos/id/2/200/300"
        ),
        primaryImageUrl = "https://picsum.photos/id/1/200/300",
        bio = "안녕하세요! 안드로이드 개발자입니다. 영화와 여행을 좋아해요.",
        job = "Software Engineer",
        favoriteMovies = listOf(
            MovieEntity(1L, "인셉션", "https://picsum.photos/id/10/200/300"),
            MovieEntity(2L, "인터스텔라", "https://picsum.photos/id/11/200/300")
        ),
        preferredGenres = listOf(
            GenreEntity(1L, "SF"),
            GenreEntity(2L, "액션"),
            GenreEntity(3L, "스릴러")
        ),
        // OAuth 및 온보딩 관련 필드
        email = "gemini@someverse.com",
        realName = "김철수",
        provider = "GOOGLE",
        birthDate = "1996-05-20T00:00:00.000+00:00",
        phone = "010-1234-5678",
        onboardingCompleted = true,
        onboardingStep = 1,
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis()
    )

    private val existingNicknames = mutableSetOf("썸버스", "안드로이드", "제미나이", "관리자")

    // ==================== Authentication ====================

    override suspend fun getAuthStatus(): Pair<UserEntity, Boolean> {
        delay(500)

        // Generate mock auth status response (simulates GET /users/me)
        // Note: This is a lightweight response, not full profile
        val mockUser =
            UserEntity(
                id = "user_${System.currentTimeMillis()}",
                nickname = null, // Not included in auth status response
                age = null,
                gender = "FEMALE",
                activityLocations = null,
                profileImages = null,
                primaryImageUrl = null,
                bio = null,
                job = null,
                favoriteMovies = null,
                preferredGenres = null,
                // OAuth-specific fields
                email = "user@example.com",
                realName = "홍길동",
                provider = "KAKAO",
                birthDate = "1997-03-17T00:00:00.000+00:00",
                phone = "01012345678",
                onboardingCompleted = false, // For testing onboarding flow
                onboardingStep = 0,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
            )

        saveUser(mockUser)

        println("📱 Local: Auth status retrieved (Mock) - Onboarding: ${mockUser.onboardingCompleted}")
        return Pair(mockUser, mockUser.onboardingCompleted)
    }

    override suspend fun logout() {
        delay(200)
        clearAuth()
    }

    override suspend fun refreshToken(refreshToken: String): AuthTokenEntity {
        delay(300)

        val newToken =
            AuthTokenEntity(
                accessToken = "mock_refreshed_access_token_${System.currentTimeMillis()}",
                refreshToken = "mock_refreshed_refresh_token_${System.currentTimeMillis()}",
                expiresIn = 3600000,
                tokenType = "Bearer",
            )

        saveToken(newToken)
        println(" Local: Token refreshed (Mock)")
        return newToken
    }

    override suspend fun isAuthenticated(): Boolean {
        delay(50)
        return isAuthenticatedFlag && storedToken != null
    }

    override suspend fun clearAuth() {
        delay(50)
        storedToken = null
        isAuthenticatedFlag = false
        currentUser = null
        println(" Local: Auth data cleared")
    }

    override suspend fun getToken(): AuthTokenEntity? {
        delay(50)
        return storedToken
    }

    override suspend fun saveToken(token: AuthTokenEntity) {
        delay(50)
        storedToken = token
        isAuthenticatedFlag = true
        println(" Local: Token saved")
    }

    // ==================== Onboarding Data ====================

    override suspend fun checkNickname(nickname: String): Boolean {
        return !existingNicknames.contains(nickname)
    }

    override suspend fun submitNickname(nickname: String): UserEntity {
        delay(100)

        // TODO: Update based on actual backend API for onboarding
        val updatedUser =
            currentUser?.copy(
                realName = nickname, // Temporary: use realName for nickname
                updatedAt = System.currentTimeMillis(),
            ) ?: throw IllegalStateException("No user logged in")
        saveUser(updatedUser)
        println(" Local: Nickname saved -> $nickname")
        return updatedUser
    }

    override suspend fun submitGender(gender: Gender): UserEntity {
        delay(100)

        val updatedUser =
            currentUser?.copy(
                gender = gender.name,
                updatedAt = System.currentTimeMillis(),
            ) ?: throw IllegalStateException("No user logged in")

        saveUser(updatedUser)
        println(" Local: Gender saved -> $gender")
        return updatedUser
    }

    override suspend fun submitAge(age: Int): UserEntity {
        delay(100)

        // TODO: Update based on actual backend API for age/birthDate
        // For now, just update the user
        val updatedUser =
            currentUser?.copy(
                updatedAt = System.currentTimeMillis(),
            ) ?: throw IllegalStateException("No user logged in")

        saveUser(updatedUser)
        println(" Local: Age saved -> $age")
        return updatedUser
    }

    override suspend fun submitAddress(address: List<String>): UserEntity {
        delay(100)

        // TODO: Update based on actual backend API for address
        val updatedUser =
            currentUser?.copy(
                // In a real implementation, we would save the address list to a field
                // For now, just updating the timestamp as before
                updatedAt = System.currentTimeMillis(),
            ) ?: throw IllegalStateException("No user logged in")

        saveUser(updatedUser)
        println(" Local: Addresses saved -> $address")
        return updatedUser
    }

    override suspend fun submitProfileImage(imageUrl: String): UserEntity {
        delay(100)

        // Mark onboarding as completed when profile image is submitted (last step)
        val updatedUser =
            currentUser?.copy(
                primaryImageUrl = imageUrl,
                onboardingCompleted = true,
                updatedAt = System.currentTimeMillis(),
            ) ?: throw IllegalStateException("No user logged in")

        saveUser(updatedUser)
        println(" Local: Profile image saved -> $imageUrl")
        return updatedUser
    }

    // ==================== User Management ====================

    override suspend fun getCurrentUser(): UserEntity? {
        delay(50)
        return currentUser
    }

    override suspend fun saveUser(user: UserEntity) {
        delay(50)
        currentUser = user
    }

    // ==================== Helper Methods ====================

    /**
     * Get all stored data (for debugging)
     */
    fun getAllData(): Map<String, Any?> =
        mapOf(
            "isAuthenticated" to isAuthenticatedFlag,
            "token" to storedToken,
            "user" to currentUser,
        )

    override suspend fun getAddressList(): List<Location> {
        // Simulated delay
        delay(300)

        // Return mock address list data
        return listOf(
            Location("서울특별시", "강남구"),
            Location("서울특별시", "서초구"),
            Location("서울특별시", "마포구"),
            Location("서울특별시", "종로구"),
            Location("서울특별시", "영등포구"),
            Location("경기도", "성남시"),
            Location("경기도", "수원시"),
            Location("경기도", "화성시"),
            Location("경기도", "의정부시"),
            Location("부산광역시", "해운대구"),
            Location("부산광역시", "중구"),
            Location("인천광역시", "중구"),
            Location("인천광역시", "연수구"),
        )
    }
}
