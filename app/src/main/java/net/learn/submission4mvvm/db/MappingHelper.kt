package net.learn.submission4mvvm.db

import android.database.Cursor
import net.learn.submission4mvvm.model.movies.Movie

object MappingHelper {
    fun mapping(favCursor: Cursor): ArrayList<Movie> {
        val favList = ArrayList<Movie>()
//        favCursor.moveToFirst()
        while (favCursor.moveToNext()) {
            val fId = favCursor.getInt(favCursor.getColumnIndexOrThrow(DBFavorite.Columns._ID))
            val backdrop =
                favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Columns.BACKDROP))
            val id = favCursor.getInt(favCursor.getColumnIndexOrThrow(DBFavorite.Columns.ID))
            val language =
                favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Columns.ORIGINAL_LANG))
            val overview =
                favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Columns.OVERVIEW))
            val poster =
                favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Columns.POSTER))
            val release =
                favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Columns.RELEASE_DATE))
            val title =
                favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Columns.TITLE))
            val rating =
                favCursor.getDouble(favCursor.getColumnIndexOrThrow(DBFavorite.Columns.RATING))
            val fType =
                favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Columns.fTYPE))
            favList.add(
                Movie(
                    fId,
                    backdrop,
                    id,
                    language,
                    overview,
                    poster,
                    release,
                    title,
                    rating,
                    fType
                )
            )
        }
        return favList
    }
}