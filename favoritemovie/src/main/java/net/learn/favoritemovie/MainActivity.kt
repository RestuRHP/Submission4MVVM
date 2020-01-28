package net.learn.favoritemovie

import android.database.ContentObserver
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.learn.favoritemovie.db.DBFavorite
import net.learn.favoritemovie.db.MappingHelper

class MainActivity : AppCompatActivity() {

    private lateinit var adapterBase: BaseFavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapterBase = BaseFavoriteAdapter()
        adapterBase.notifyDataSetChanged()
        val movieList: RecyclerView = findViewById(R.id.rv_movies)
        movieList.setHasFixedSize(true)
        movieList.layoutManager = LinearLayoutManager(this)
        movieList.adapter = adapterBase

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadFavorite()
            }
        }
        contentResolver.registerContentObserver(DBFavorite.Columns().CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadFavorite()
        }
    }

    private fun loadFavorite() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(
                    DBFavorite.Columns().CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                ) as Cursor
                MappingHelper.mapping(cursor)
            }
            val favorite = deferredFavorite.await()
            Log.d("GET DATA  Movies: ", "$favorite")
            if (favorite.size > 0) {
                adapterBase.listFavorite = favorite
            }
        }
    }


    override fun onResume() {
        super.onResume()
        adapterBase.listFavorite.clear()
        loadFavorite()
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }
}
