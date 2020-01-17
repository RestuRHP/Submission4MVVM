package net.learn.submission4mvvm.ui.favorite.tab


import android.annotation.SuppressLint
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.favorite_display.*
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.db.DBFavorite
import net.learn.submission4mvvm.db.Helper
import net.learn.submission4mvvm.db.MappingHelper
import net.learn.submission4mvvm.model.movies.Movie
import net.learn.submission4mvvm.ui.movies.MoviesAdapter

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMoviesProvider : Fragment() {
    private lateinit var adapter: MoviesAdapter
    private lateinit var helper: Helper
    private lateinit var listFavorite: ArrayList<Movie>

    private var Thread: HandlerThread? = null
    private var mObserver: DataObserver? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_display, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helper = Helper(context!!)
        helper.open()

        loadFavorite()
    }

    @SuppressLint("Recycle")
    private fun loadFavorite(){
        Thread = HandlerThread("DataObserver")
        Thread?.start()
        val handler = Handler(Thread?.looper)
        mObserver = DataObserver(handler,context as Context)
        context?.contentResolver?.registerContentObserver(DBFavorite.Columns().CONTENT_URI,true,mObserver as ContentObserver)

        val list = context?.contentResolver?.query(DBFavorite.Columns().CONTENT_URI,
            null,
            null,
            null,
            null)

        listFavorite = MappingHelper.maping(list as Cursor)

        Log.d("List Provider","$listFavorite")

        adapter = MoviesAdapter()
        adapter.setData(listFavorite)
        adapter.notifyDataSetChanged()
        rv_movies.setHasFixedSize(true)
        rv_movies.layoutManager = LinearLayoutManager(this.context)
        rv_movies.adapter=adapter

        progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        helper.close()
    }

    override fun onResume() {
        super.onResume()
        listFavorite.clear()
        loadFavorite()

    }

    class DataObserver(handler: Handler, internal val context: Context) : ContentObserver(handler)

}
