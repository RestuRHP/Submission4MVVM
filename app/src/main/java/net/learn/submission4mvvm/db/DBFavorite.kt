package net.learn.submission4mvvm.db

import android.net.Uri
import android.provider.BaseColumns


class DBFavorite {

    val AUTHORITY = "net.learn.submission4mvvm"
    private val SCHEME = "content"

    internal class Columns : BaseColumns {
        companion object {
            const val TABLE_NAME = "tb_favorite"
            const val _ID = "fId"
            const val ID = "id"
            const val TITLE = "title"
            const val BACKDROP = "backdropPath"
            const val ORIGINAL_LANG = "originalLanguage"
            const val POSTER = "posterPath"
            const val OVERVIEW = "overview"
            const val RELEASE_DATE = "releaseDate"
            const val RATING = "voteAverage"
            const val fTYPE = "fType"
        }

        val CONTENT_URI: Uri = Uri.Builder().scheme(DBFavorite().SCHEME)
            .authority(DBFavorite().AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }

}