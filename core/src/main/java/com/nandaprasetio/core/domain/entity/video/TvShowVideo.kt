package com.nandaprasetio.core.domain.entity.video

import android.os.Parcel
import android.os.Parcelable
import java.util.*

open class TvShowVideo(
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
    val firstAirDate: Date?,
    val originalCountry: List<String?>
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
        parcel.readValue(Date::class.java.classLoader) as Date?,
        mutableListOf<String?>().apply {
            parcel.readList(this as List<String?>, Int::class.java.classLoader)
        }
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
        dest.writeValue(firstAirDate)
        dest.writeList(originalCountry)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as TvShowVideo

        if (firstAirDate != other.firstAirDate) return false
        if (originalCountry != other.originalCountry) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (firstAirDate?.hashCode() ?: 0)
        result = 31 * result + originalCountry.hashCode()
        return result
    }

    companion object CREATOR : Parcelable.Creator<TvShowVideo> {
        override fun createFromParcel(parcel: Parcel): TvShowVideo {
            return TvShowVideo(parcel)
        }

        override fun newArray(size: Int): Array<TvShowVideo?> {
            return arrayOfNulls(size)
        }
    }
}