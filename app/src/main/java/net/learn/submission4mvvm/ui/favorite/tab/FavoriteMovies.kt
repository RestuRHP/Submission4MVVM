package net.learn.submission4mvvm.ui.favorite.tab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detai_item.progressBar
import kotlinx.android.synthetic.main.fragment_display.rv_movies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.db.Helper
import net.learn.submission4mvvm.db.MappingHelper
import net.learn.submission4mvvm.ui.base.BaseFavoriteAdapter

class FavoriteMovies : Fragment() {
    private lateinit var adapterBase: BaseFavoriteAdapter
    private lateinit var helper: Helper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorite_display, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterBase = BaseFavoriteAdapter()
        adapterBase.notifyDataSetChanged()
        val movieList: RecyclerView = view.findViewById(R.id.rv_movies)
        movieList.setHasFixedSize(true)
        movieList.layoutManager = LinearLayoutManager(this.context)
        movieList.adapter = adapterBase

        loadFavorite()

        helper = Helper(context!!)
        helper.open()
    }

    private fun loadFavorite() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = helper.queryByType("movie")
                MappingHelper.mapping(cursor)
            }
            progressBar.visibility = View.INVISIBLE
            val favorite = deferredFavorite.await()
            Log.d("GET DATA  Movies: ", "$favorite")
            if (favorite.size > 0) {
                adapterBase.listFavorite = favorite
            } else {
                adapterBase.listFavorite = ArrayList()
                showSnackBarMessage("Tidak ada data saat ini")
            }
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(rv_movies, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        helper.close()
    }

    override fun onResume() {
        super.onResume()
        helper.open()
        adapterBase.listFavorite.clear()
        loadFavorite()
        rv_movies.adapter?.notifyDataSetChanged()
    }
}