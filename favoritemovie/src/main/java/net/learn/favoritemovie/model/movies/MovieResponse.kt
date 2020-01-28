package net.learn.submission4.data.response


import com.google.gson.annotations.SerializedName
import net.learn.favoritemovie.model.movies.Movie

data class MovieResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<Movie>,
    @SerializedName("total_pages")
    var totalPages: Int
)