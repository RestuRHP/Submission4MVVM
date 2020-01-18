package net.learn.submission4mvvm.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion.BACKDROP
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion.ID
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion.ORIGINAL_LANG
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion.OVERVIEW
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion.POSTER
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion.RATING
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion.RELEASE_DATE
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion.TITLE
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion.fTYPE
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion._ID
import net.learn.submission4mvvm.model.movies.Movie
import java.sql.SQLException

class Helper(context: Context) {

    companion object{
        private const val DATABASE_TABLE = DBFavorite.Columns.TABLE_NAME
        private lateinit var dataHelper:DatabaseHelper
        private lateinit var dataBase: SQLiteDatabase
    }

    init {
        dataHelper= DatabaseHelper(context)
    }


    @Throws(SQLException::class)
    fun open() {
        dataBase = dataHelper.writableDatabase
    }

    fun close() {
        dataHelper.close()
        if (dataBase.isOpen)
            dataBase.close()
    }

    fun queryAll(type: String): ArrayList<Movie> {
        val arrayList = ArrayList<Movie>()
        val cursor = dataBase.query(
            DATABASE_TABLE,
            null,
            "$fTYPE = '$type'",
            null,
            null,
            null,
            "$_ID ASC",
            null
        )
        cursor.moveToFirst()
        var movie: Movie
        if (cursor.count>0){
            do{
                movie = Movie(
                    cursor.getInt(cursor.getColumnIndexOrThrow(_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANG)),
                    cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)),
                    cursor.getString(cursor.getColumnIndexOrThrow(POSTER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(RATING)),
                    cursor.getString(cursor.getColumnIndexOrThrow(fTYPE))
                )
                arrayList.add(movie)
                cursor.moveToNext()
            }while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    fun queryAll2(): Cursor {
        return dataBase.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null)
    }

    fun queryById(id: String): Cursor {
        return dataBase.query(
            DATABASE_TABLE,
            null,
            "$ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun queryByType(type:String):Cursor{
        return dataBase.query(
            DATABASE_TABLE,
            null,
            "$fTYPE = '$type'",
            null,
            null,
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long {
        return dataBase.insert(DATABASE_TABLE, null, values)
    }

    fun deleteById(id: String): Int {
        return dataBase.delete(DATABASE_TABLE, "$ID = '$id'", null)
    }

    fun isMovieFavorited(id: String): Boolean {
        val cursor = dataBase.query(
            DATABASE_TABLE,
            null,
            "$ID = '$id'",
            null,
            null,
            null,
            null,
            null
        )
        cursor.moveToFirst()
        if (cursor.count > 0) {
            return true
        }
        return false
    }

    fun queryByIdProvider(id: String): Cursor {
        return dataBase.query(DATABASE_TABLE, null, "$ID = ?", arrayOf(id), null,
            null, null, null)
    }

    fun queryProvider(): Cursor {
        return dataBase.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    fun insertProvider(values: ContentValues): Long {
        return dataBase.insert(DATABASE_TABLE, null, values)
    }

    fun updateProvider(id: String, values: ContentValues): Int {
        return dataBase.update(DATABASE_TABLE, values, "$ID = ?", arrayOf(id))
    }

    fun deleteProvider(id: String): Int {
        return dataBase.delete(DATABASE_TABLE, "$ID = ?", arrayOf(id))
    }
}