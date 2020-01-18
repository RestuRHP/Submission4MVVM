package net.learn.submission4mvvm.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

internal class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "db_favorite"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE = "CREATE TABLE ${DBFavorite.Columns.TABLE_NAME}"+
                "(${DBFavorite.Columns._ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "${DBFavorite.Columns.fTYPE} TEXT NOT NULL,"+
                "${DBFavorite.Columns.ID} INTEGER NOT NULL,"+
                "${DBFavorite.Columns.TITLE} TEXT NOT NULL,"+
                "${DBFavorite.Columns.BACKDROP} TEXT NOT NULL,"+
                "${DBFavorite.Columns.ORIGINAL_LANG} TEXT NOT NULL,"+
                "${DBFavorite.Columns.POSTER} TEXT NOT NULL,"+
                "${DBFavorite.Columns.OVERVIEW} TEXT NOT NULL,"+
                "${DBFavorite.Columns.RELEASE_DATE} TEXT NOT NULL,"+
                "${DBFavorite.Columns.RATING} FLOAT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_TABLE)
        Log.d("Create Table SQLIte", SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DBFavorite.Columns.TABLE_NAME}")
        onCreate(db)
    }

}