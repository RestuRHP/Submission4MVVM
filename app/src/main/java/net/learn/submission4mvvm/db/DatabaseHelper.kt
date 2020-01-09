package net.learn.submission4mvvm.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "dbfavorite"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE = "CREATE TABLE ${DBFavorite.Colums.TABLE_NAME}"+
                "(${DBFavorite.Colums._ID} INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "${DBFavorite.Colums.ID} INTEGER,"+
                "${DBFavorite.Colums.TITLE} TEXT ,"+
                "${DBFavorite.Colums.BACKDROP} TEXT,"+
                "${DBFavorite.Colums.ORIGINAL_LANG} TEXT,"+
                "${DBFavorite.Colums.POSTER} TEXT,"+
                "${DBFavorite.Colums.OVERVIEW} TEXT,"+
                "${DBFavorite.Colums.RELEASE_DATE} TEXT,"+
                "${DBFavorite.Colums.RATING} FLOAT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DBFavorite.Colums.TABLE_NAME}")
        onCreate(db)
    }

}