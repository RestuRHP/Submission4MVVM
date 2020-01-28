package net.learn.favoritemovie.model.movies

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var fId: Int?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date", alternate = ["first_air_date"])
    var releaseDate: String?,
    @SerializedName("title", alternate = ["original_name"])
    var title: String?,
    @SerializedName("vote_average")
    var voteAverage: Double?,
    var fType: String?
) : Parcelable