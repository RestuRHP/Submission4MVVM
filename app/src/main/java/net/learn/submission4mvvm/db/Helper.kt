package net.learn.submission4mvvm.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion.fTYPE
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion._ID
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

    fun queryAll(): Cursor {
        return dataBase.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    fun queryById(id: String): Cursor {
        return dataBase.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
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
            "$fTYPE = ?",
            arrayOf(type),
            null,
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long {
        return dataBase.insert(DATABASE_TABLE, null, values)
    }

    fun deleteById(id: String): Int {
        return dataBase.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

    fun queryByIdProvider(id: String): Cursor {
        return dataBase.query(DATABASE_TABLE, null, "$_ID = ?", arrayOf(id), null,
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
        return dataBase.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteProvider(id: String): Int {
        return dataBase.delete(DATABASE_TABLE, "$_ID = ?", arrayOf(id))
    }
}