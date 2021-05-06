package com.nandaprasetio.core.domain.entity.video

import android.os.Parcel
import android.os.Parcelable
import java.util.*

open class MovieVideo(
    id: Int,
    titleOrName: String?,
    originalTitleOrName: String?,
    originalLanguange: String?,
    genresId: List<Int>,
    overview: String?,
    posterPath: String?,
    backdropPath: String?,
    popularity: Float,
    voteCount: Int,
    voteAverage: Float,
    val adult: Boolean,
    val releaseDate: Date?,
    val video: Boolean
): BaseVideo(
    id, titleOrName, originalTitleOrName, originalLanguange, genresId, overview, posterPath,
    backdropPath, popularity, voteCount, voteAverage
), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        mutableListOf<Int>().apply {
            parcel.readList(this as List<Int>, Int::class.java.classLoader)
        },
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readValue(Boolean::class.java.classLoader) as Boolean,
        parcel.readValue(Date::class.java.classLoader) as Date?,
        parcel.readValue(Boolean::class.java.classLoader) as Boolean
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(titleOrName)
        dest.writeString(originalTitleOrName)
        dest.writeString(originalLanguange)
        dest.writeList(genresId)
        dest.writeString(overview)
        dest.writeString(posterPath)
        dest.writeString(backdropPath)
        dest.writeFloat(popularity)
        dest.writeInt(voteCount)
        dest.writeFloat(voteAverage)
        dest.writeValue(adult)
        dest.writeValue(releaseDate)
        dest.writeValue(video)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as MovieVideo

        if (adult != other.adult) return false
        if (releaseDate != other.releaseDate) return false
        if (video != other.video) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + adult.hashCode()
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + video.hashCode()
        return result
    }


    companion object CREATOR: Parcelable.Creator<MovieVideo> {
        override fun createFromParcel(parcel: Parcel): MovieVideo {
            return MovieVideo(parcel)
        }

        override fun newArray(size: Int): Array<MovieVideo?> {
            return arrayOfNulls(size)
        }
    }
}