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
                    content = "우주 과학을 몰라도 감동적인 부성애... 눈물 펑펑 쏟고 왔습니다.",
                    nickName = "무비마스터",
                    profileImages = "https://picsum.photos/id/1/200/200",
                    movieId = 157336,
                    movieTitle = "인터스텔라",
                    movieOverview = "우주를 배경으로 한 SF 대작",
                    moviePosterPath = "https://picsum.photos/id/1/200/200",
                    movieReleaseDate = "2014-11-06",
                    createdAt = "2024-12-20T14:30:00"
                ),
                FeedEntity(
                    id = 2,
                    content = "인셉션은 봐도 봐도 새로워요. 팽이가 멈췄을까요?",
                    nickName = "꿈꾸는고양이",
                    profileImages = "https://picsum.photos/id/2/200/200",
                    movieId = 27205,
                    movieTitle = "인셉션",
                    movieOverview = "타인의 꿈속에 침투해 생각을 심는 작전",
                    moviePosterPath = "https://picsum.photos/id/2/200/200",
                    movieReleaseDate = "2010-07-21",
                    createdAt = "2024-12-21T09:15:00"
                ),
                FeedEntity(
                    id = 3,
                    content = "조커의 웃음소리가 아직도 귓가에 맴도네요. 연출이 미쳤습니다.",
                    nickName = "고담시민",
                    profileImages = "https://picsum.photos/id/3/200/200",
                    movieId = 475557,
                    movieTitle = "조커",
                    movieOverview = "희극인 모델을 꿈꾸는 아서 플렉의 타락",
                    moviePosterPath = "https://picsum.photos/id/3/200/200",
                    movieReleaseDate = "2019-10-02",
                    createdAt = "2024-12-22T11:00:00"
                ),
                FeedEntity(
                    id = 4,
                    content = "기생충은 정말 한국 영화의 자부심입니다. 디테일이 살아있어요.",
                    nickName = "봉테일덕후",
                    profileImages = "https://picsum.photos/id/4/200/200",
                    movieId = 496243,
                    movieTitle = "기생충",
                    movieOverview = "전혀 다른 두 가족의 만남이 가져온 비극",
                    moviePosterPath = "https://picsum.photos/id/4/200/200",
                    movieReleaseDate = "2019-05-30",
                    createdAt = "2024-12-22T18:45:00"
                ),
                FeedEntity(
                    id = 5,
                    content = "어바웃 타임 보고 나니 오늘 하루가 소중해지네요. 추천합니다!",
                    nickName = "사랑비",
                    profileImages = "https://picsum.photos/id/5/200/200",
                    movieId = 122906,
                    movieTitle = "어바웃 타임",
                    movieOverview = "시간을 되돌릴 수 있는 능력을 가진 남자의 사랑 이야기",
                    moviePosterPath = "https://picsum.photos/id/5/200/200",
                    movieReleaseDate = "2013-12-05",
                    createdAt = "2024-12-23T20:00:00"
                ),
                FeedEntity(
                    id = 6,
                    content = "탑건: 매버릭은 반드시 영화관에서 봐야 합니다. 박진감 최고!",
                    nickName = "스카이워커",
                    profileImages = "https://picsum.photos/id/10/200/200",
                    movieId = 361743,
                    movieTitle = "탑건: 매버릭",
                    movieOverview = "최고의 파일럿 매버릭과 새로운 팀의 불가능한 미션",
                    moviePosterPath = "https://picsum.photos/id/10/200/200",
                    movieReleaseDate = "2022-06-22",
                    createdAt = "2024-12-24T15:20:00"
                ),
                FeedEntity(
                    id = 7,
                    content = "아이언맨의 시작... 다시 봐도 토니 스타크는 멋있네요.",
                    nickName = "어벤져스어셈블",
                    profileImages = "https://picsum.photos/id/12/200/200",
                    movieId = 1726,
                    movieTitle = "아이언맨",
                    movieOverview = "천재 발명가 토니 스타크의 히어로 탄생기",
                    moviePosterPath = "https://picsum.photos/id/12/200/200",
                    movieReleaseDate = "2008-04-30",
                    createdAt = "2024-12-25T13:00:00"
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
