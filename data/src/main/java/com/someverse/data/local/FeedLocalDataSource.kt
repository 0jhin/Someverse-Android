package com.someverse.data.local

import com.someverse.data.model.FeedEntity
import com.someverse.data.source.FeedDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Feed Local DataSource (Mock Implementation)
 * - Implements FeedDataSource interface
 * - Handles local/mock data for development
 * - Simulates API delays
 * - Can be replaced with Room database later for offline support
 */
@Singleton
class FeedLocalDataSource
@Inject
constructor() : FeedDataSource {
    // In-memory storage for mock data
    private val feeds = mutableListOf<FeedEntity>()
    private val myFeeds = mutableListOf<FeedEntity>()
    private var nextId = 1L

    init {
        // Initialize with some mock data
        feeds.addAll(
            listOf(
                FeedEntity(
                    id = nextId++,
                    content = "최고의 영화입니다. 다들 꼭 보시길 추천드립니다.",
                    nickName = "김민성이다",
                    profileImages = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "",
                    movieOverview = "",
                    moviePosterPath = "",
                    movieReleaseDate = "",
                    createdAt = ""
                ),
                FeedEntity(
                    id = nextId++,
                    content = "재밌습니다재밌습니다재밌습니다..",
                    nickName = "김민성이다",
                    profileImages = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "",
                    movieOverview = "",
                    moviePosterPath = "",
                    movieReleaseDate = "",
                    createdAt = ""
                ),
                FeedEntity(
                    id = nextId++,
                    content = "체인소맨 처음봤는데 액션도 화려하고 재밌게 봤습니다!",
                    nickName = "김민성이다",
                    profileImages = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "",
                    movieOverview = "",
                    moviePosterPath = "",
                    movieReleaseDate = "",
                    createdAt = ""
                ),
                FeedEntity(
                    id = nextId++,
                    content = "귀멸의 칼날 재밌게 보긴 했는데 회상씬이 너무 길어요",
                    nickName = "김민성이다",
                    profileImages = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "",
                    movieOverview = "",
                    moviePosterPath = "",
                    movieReleaseDate = "",
                    createdAt = ""
                ),
                FeedEntity(
                    id = nextId++,
                    content = "케데헌 안봤지만 노래만 알아요",
                    nickName = "김민성이다",
                    profileImages = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "",
                    movieOverview = "",
                    moviePosterPath = "",
                    movieReleaseDate = "",
                    createdAt = ""
                ),
            ),
        )
        myFeeds.addAll(
            listOf(
                FeedEntity(
                    id = 1,
                    content = "오늘 본 인터스텔라 정말 최고였어요!",
                    nickName = "보름달투",
                    profileImages = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "인터스텔라",
                    movieOverview = "우주를 배경으로 한 SF 대작",
                    moviePosterPath = "path/to/poster.jpg",
                    movieReleaseDate = "2014-11-07",
                    createdAt = "2024-12-22T10:00:00"
                ),
                FeedEntity(
                    id = 1,
                    content = "오늘 본 인터스텔라 정말 최고였어요!",
                    nickName = "보름달투",
                    profileImages = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "인터스텔라",
                    movieOverview = "우주를 배경으로 한 SF 대작",
                    moviePosterPath = "path/to/poster.jpg",
                    movieReleaseDate = "2014-11-07",
                    createdAt = "2024-12-22T10:00:00"
                ),
                FeedEntity(
                    id = 1,
                    content = "오늘 본 인터스텔라 정말 최고였어요!",
                    nickName = "보름달투",
                    profileImages = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "인터스텔라",
                    movieOverview = "우주를 배경으로 한 SF 대작",
                    moviePosterPath = "path/to/poster.jpg",
                    movieReleaseDate = "2014-11-07",
                    createdAt = "2024-12-22T10:00:00"
                ),
                FeedEntity(
                    id = 1,
                    content = "오늘 본 인터스텔라 정말 최고였어요!",
                    nickName = "보름달투",
                    profileImages = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "인터스텔라",
                    movieOverview = "우주를 배경으로 한 SF 대작",
                    moviePosterPath = "path/to/poster.jpg",
                    movieReleaseDate = "2014-11-07",
                    createdAt = "2024-12-22T10:00:00"
                ),
                FeedEntity(
                    id = 1,
                    content = "오늘 본 인터스텔라 정말 최고였어요!",
                    nickName = "보름달투",
                    profileImages = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "인터스텔라",
                    movieOverview = "우주를 배경으로 한 SF 대작",
                    moviePosterPath = "path/to/poster.jpg",
                    movieReleaseDate = "2014-11-07",
                    createdAt = "2024-12-22T10:00:00"
                ),
                FeedEntity(
                    id = 1,
                    content = "오늘 본 인터스텔라 정말 최고였어요!",
                    nickName = "보름달투",
                    profileImages = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "인터스텔라",
                    movieOverview = "우주를 배경으로 한 SF 대작",
                    moviePosterPath = "path/to/poster.jpg",
                    movieReleaseDate = "2014-11-07",
                    createdAt = "2024-12-22T10:00:00"
                ),
                FeedEntity(
                    id = 1,
                    content = "오늘 본 인터스텔라 정말 최고였어요!",
                    nickName = "보름달투",
                    profileImages = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "인터스텔라",
                    movieOverview = "우주를 배경으로 한 SF 대작",
                    moviePosterPath = "path/to/poster.jpg",
                    movieReleaseDate = "2014-11-07",
                    createdAt = "2024-12-22T10:00:00"
                ),
            ),
        )
    }

    override suspend fun createFeed(
        feedType: String,
        movieId: Long?,
        musicId: Long?,
        content: String,
    ): FeedEntity {
        delay(500) // Simulate network delay

        val newFeed =
            FeedEntity(
                id = nextId++,
                content = content,
                nickName = "김민성이다",
                profileImages = "https://example.com/image1.jpg",
                movieId = 157336,
                movieTitle = "",
                movieOverview = "",
                moviePosterPath = "",
                movieReleaseDate = "",
                createdAt = ""
            )

        feeds.add(0, newFeed) // Add to beginning
        println("📝 Local: Feed created with ID ${newFeed.id}")
        return newFeed
    }

    override suspend fun getRandomFeeds(): List<FeedEntity> {
        delay(500)
        // Return random feeds (shuffle and take 5)
        val randomFeeds = feeds.shuffled().take(5)
        println("📝 Local: Fetched ${randomFeeds.size} random feeds")
        return randomFeeds
    }

    override suspend fun getMyFeeds(): List<FeedEntity> {
        delay(500)
        println("📝 Local: Fetched ${myFeeds.size} my feeds")
        return myFeeds.toList()
    }

    override suspend fun getFeedDetail(feedId: Long): FeedEntity {
        delay(300)
        val feed =
            feeds.find { it.id == feedId }
                ?: throw NoSuchElementException("Feed with ID $feedId not found")
        println("📝 Local: Fetched feed detail for ID $feedId")
        return feed
    }

    override suspend fun updateFeed(
        feedId: Long,
        content: String,
    ): FeedEntity {
        delay(500)

        val index = feeds.indexOfFirst { it.id == feedId }
        if (index == -1) {
            throw NoSuchElementException("Feed with ID $feedId not found")
        }

        val updatedFeed = feeds[index].copy(content = content)
        feeds[index] = updatedFeed

        println("📝 Local: Updated feed with ID $feedId")
        return updatedFeed
    }

    override suspend fun inactivateFeed(feedId: Long): Boolean {
        delay(300)

        val removed = feeds.removeIf { it.id == feedId }
        println("📝 Local: Inactivated feed with ID $feedId - Success: $removed")
        return removed
    }

    override suspend fun activateFeed(feedId: Long): FeedEntity {
        delay(500)

        // In mock implementation, just return a feed
        // In real implementation, this would restore an inactive feed
        val feed =
            feeds.find { it.id == feedId }
                ?: throw NoSuchElementException("Feed with ID $feedId not found")

        println("📝 Local: Activated feed with ID $feedId")
        return feed
    }

    override suspend fun inactivateAllFeeds(): Boolean {
        delay(500)

        val count = feeds.size
        feeds.clear()

        println("📝 Local: Inactivated all $count feeds")
        return true
    }
}
