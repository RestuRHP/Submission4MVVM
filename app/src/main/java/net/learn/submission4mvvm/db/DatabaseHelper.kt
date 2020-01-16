package net.learn.submission4mvvm.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

internal class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "dbfavorite"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE = "CREATE TABLE ${DBFavorite.Columns.TABLE_NAME}"+
                "(${DBFavorite.Columns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "${DBFavorite.Columns.fTYPE} TEXT ,"+
                "${DBFavorite.Columns.ID} INTEGER,"+
                "${DBFavorite.Columns.TITLE} TEXT ,"+
                "${DBFavorite.Columns.BACKDROP} TEXT,"+
                "${DBFavorite.Columns.ORIGINAL_LANG} TEXT,"+
                "${DBFavorite.Columns.POSTER} TEXT,"+
                "${DBFavorite.Columns.OVERVIEW} TEXT,"+
                "${DBFavorite.Columns.RELEASE_DATE} TEXT,"+
                "${DBFavorite.Columns.RATING} FLOAT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_TABLE)
        Log.d("Create Table SQLIte","$SQL_CREATE_TABLE")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DBFavorite.Columns.TABLE_NAME}")
        onCreate(db)
    }

}