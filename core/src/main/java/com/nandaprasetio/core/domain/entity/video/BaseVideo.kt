package com.nandaprasetio.core.domain.entity.video

abstract class BaseVideo(
    val id: Int,
    val titleOrName: String?,
    val originalTitleOrName: String?,
    val originalLanguange: String?,
    val genresId: List<Int>,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val popularity: Float,
    val voteCount: Int,
    val voteAverage: Float
) {
    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (titleOrName?.hashCode() ?: 0)
        result = 31 * result + (originalTitleOrName?.hashCode() ?: 0)
        result = 31 * result + (originalLanguange?.hashCode() ?: 0)
        result = 31 * result + genresId.hashCode()
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + popularity.hashCode()
        result = 31 * result + voteCount
        result = 31 * result + voteAverage.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseVideo

        if (id != other.id) return false
        if (titleOrName != other.titleOrName) return false
        if (originalTitleOrName != other.originalTitleOrName) return false
        if (originalLanguange != other.originalLanguange) return false
        if (genresId != other.genresId) return false
        if (overview != other.overview) return false
        if (posterPath != other.posterPath) return false
        if (backdropPath != other.backdropPath) return false
        if (popularity != other.popularity) return false
        if (voteCount != other.voteCount) return false
        if (voteAverage != other.voteAverage) return false

        return true
    }
}