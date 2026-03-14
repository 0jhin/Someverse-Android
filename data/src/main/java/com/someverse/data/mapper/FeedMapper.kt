package com.someverse.data.mapper

import com.someverse.data.model.CreateFeedRequestDto
import com.someverse.data.model.FeedEntity
import com.someverse.data.model.UpdateFeedRequestDto
import com.someverse.domain.model.Feed
import com.someverse.domain.model.FeedType

/**
 * Feed Mapper
 * - Converts between Entity (DTO) and Domain Model
 * - Entity: Data layer (API/DB)
 * - Domain: Business logic layer
 */
object FeedMapper {
    /**
     * Convert FeedEntity to Feed domain model
     */
    fun FeedEntity.toDomain(): Feed =
        Feed(
            id = this.id,
            content = this.content,
            nickName = this.nickName,
            profileImage = this.profileImages,
            movieId = this.movieId,
            movieTitle = this.movieTitle,
            movieOverview = this.movieOverview,
            moviePosterPath = this.moviePosterPath,
            movieReleaseDate = this.movieReleaseDate,
            createdAt = createdAt
        )

    /**
     * Convert Feed domain model to FeedEntity
     */
    fun Feed.toEntity(): FeedEntity =
        FeedEntity(
            id = this.id,
            content = this.content,
            nickName = this.nickName,
            profileImages = this.profileImage,
            movieId = this.movieId,
            movieTitle = this.movieTitle,
            movieOverview = this.movieOverview,
            moviePosterPath = this.moviePosterPath,
            movieReleaseDate = this.movieReleaseDate,
            createdAt = createdAt
        )

    /**
     * Convert domain parameters to CreateFeedRequestDto
     */
    fun createRequestDto(
        feedType: FeedType,
        movieId: Long?,
        musicId: Long?,
        content: String,
    ): CreateFeedRequestDto =
        CreateFeedRequestDto(
            feedType = feedType.name,
            movieId = movieId,
            musicId = musicId,
            content = content,
        )

    /**
     * Convert domain parameter to UpdateFeedRequestDto
     */
    fun updateRequestDto(content: String): UpdateFeedRequestDto =
        UpdateFeedRequestDto(content = content)
}
