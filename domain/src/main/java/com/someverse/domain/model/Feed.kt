package com.someverse.domain.model

/**
 * Feed Domain Model
 */
data class Feed(
    val id: Long,
    val content: String,
    val nickName: String,
    val profileImage: String?,
    val movieId: Long,
    val movieTitle: String,
    val movieOverview: String,
    val moviePosterPath: String,
    val movieReleaseDate: String,
    val createdAt: String
)

/**
 * Feed Type
 */
enum class FeedType {
    MOVIE,
    MUSIC,
}
