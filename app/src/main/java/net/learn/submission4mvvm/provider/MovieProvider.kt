package net.learn.submission4mvvm.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import net.learn.submission4mvvm.db.DBFavorite
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion.TABLE_NAME
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion._ID
import net.learn.submission4mvvm.db.DBFavorite.Columns.Companion.fTYPE
import net.learn.submission4mvvm.db.Helper
import net.learn.submission4mvvm.ui.favorite.tab.FavoriteMoviesProvider

class MovieProvider : ContentProvider() {

    private val MOVIE = 1
    private val MOVIEID =2
    private val MOVIE_TYPE =3
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    lateinit var helper : Helper

    private fun UriMatching(){
        sUriMatcher.addURI(DBFavorite().AUTHORITY, TABLE_NAME, MOVIE)
        sUriMatcher.addURI(DBFavorite().AUTHORITY, "$TABLE_NAME/$fTYPE", MOVIE_TYPE)
        sUriMatcher.addURI(DBFavorite().AUTHORITY, "$TABLE_NAME/$_ID", MOVIEID)

    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        helper.open()
        val del: Int
        when (sUriMatcher.match(uri)){
            MOVIEID -> del = helper.deleteProvider(uri.lastPathSegment as String)
            else -> del = 0
        }
        context!!.contentResolver.notifyChange(DBFavorite.Columns().CONTENT_URI,FavoriteMoviesProvider.DataObserver(
            Handler(), context!!
        ))
        return del
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        helper.open()
        val added: Long
        when (sUriMatcher.match(uri)){
            MOVIE -> added = helper.insertProvider(values as ContentValues)
            else -> added = 0
        }
        context?.contentResolver?.notifyChange(DBFavorite.Columns().CONTENT_URI,
            FavoriteMoviesProvider.DataObserver(Handler(),context!!))

        return Uri.parse(DBFavorite.Columns().CONTENT_URI.toString()+"/"+added)
    }

    override fun onCreate(): Boolean {
        UriMatching()
        helper = Helper(context!!)

        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        helper.open()
        val cursor : Cursor?
        when (sUriMatcher.match(uri)){
            MOVIE -> cursor = helper.queryByType("movies")
            else -> cursor = null
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        helper.open()
        val updated: Int
        when (sUriMatcher.match(uri)) {
            MOVIEID -> updated = helper.updateProvider(uri.lastPathSegment as String, values as ContentValues)
            else -> updated = 0
        }
        context!!.contentResolver.notifyChange(DBFavorite.Columns().CONTENT_URI,
            FavoriteMoviesProvider.DataObserver(Handler(), context!!))
        return updated
    }
}
