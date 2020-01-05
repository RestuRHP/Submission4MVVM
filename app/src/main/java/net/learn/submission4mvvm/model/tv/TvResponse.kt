package net.learn.submission4mvvm.model.tv


import com.google.gson.annotations.SerializedName

data class TvResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val tvShows: List<TvShows>,
    @SerializedName("total_pages")
    val totalPages: Int
)