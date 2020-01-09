package net.learn.submission4mvvm.ui.favorite


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_display.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.db.Helper
import net.learn.submission4mvvm.db.MappingHelper

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    private lateinit var adapter:FavoriteAdapter
    private lateinit var helper: Helper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_display, container, false)

        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()
        val movieList: RecyclerView = view.findViewById(R.id.rv_movies)
        movieList.setHasFixedSize(true)
        movieList.layoutManager = LinearLayoutManager(this.context)
        movieList.adapter=adapter

        loadFavorite()

        helper = Helper(context!!)
        helper.open()

        return view
    }

    private fun loadFavorite(){
        GlobalScope.launch (Dispatchers.Main){
            progressBar.visibility = View.VISIBLE
            val deferredFavorite = async(Dispatchers.IO){
                val cursor = helper.queryAll()
                MappingHelper.maping(cursor)
            }
            progressBar.visibility = View.INVISIBLE
            val favorite = deferredFavorite.await()
            Log.d("GET ALL DATA : ","$favorite")
            if (favorite.size>0){
                adapter.listFavorite =favorite
            }else{
                adapter.listFavorite = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_movies, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        helper.close()
    }
}
