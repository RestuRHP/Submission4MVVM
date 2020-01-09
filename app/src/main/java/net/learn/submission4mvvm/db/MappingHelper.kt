package net.learn.submission4mvvm.db

import android.database.Cursor
import net.learn.submission4mvvm.model.movies.Movie

object MappingHelper {
    fun maping(favCursor: Cursor):ArrayList<Movie>{
        val favList = ArrayList<Movie>()
        favCursor.moveToFirst()
        while (favCursor.moveToNext()){
            val fId = favCursor.getInt(favCursor.getColumnIndexOrThrow(DBFavorite.Colums._ID))
            val backdrop = favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Colums.BACKDROP))
            val id = favCursor.getInt(favCursor.getColumnIndexOrThrow(DBFavorite.Colums.ID))
            val language = favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Colums.ORIGINAL_LANG))
            val overview = favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Colums.OVERVIEW))
            val poster = favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Colums.POSTER))
            val release = favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Colums.RELEASE_DATE))
            val title = favCursor.getString(favCursor.getColumnIndexOrThrow(DBFavorite.Colums.TITLE))
            val rating = favCursor.getDouble(favCursor.getColumnIndexOrThrow(DBFavorite.Colums.RATING))
            favList.add(Movie(fId,backdrop,id,language,overview,poster,release,title,rating))
        }
        return favList
    }
}