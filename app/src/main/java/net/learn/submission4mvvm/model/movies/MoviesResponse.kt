package net.learn.submission4.data.response


import com.google.gson.annotations.SerializedName
import net.learn.submission4mvvm.model.movies.Movie

data class MoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int
)