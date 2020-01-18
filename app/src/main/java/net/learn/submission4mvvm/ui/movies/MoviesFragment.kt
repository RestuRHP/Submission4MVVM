package net.learn.submission4mvvm.ui.movies


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.model.movies.Movie
import net.learn.submission4mvvm.ui.base.BaseAdapter
import net.learn.submission4mvvm.ui.base.BaseViewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {

    private lateinit var adapter: BaseAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_display, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BaseAdapter()
        adapter.notifyDataSetChanged()
        val movieList: RecyclerView = view.findViewById(R.id.rv_movies)
        movieList.setHasFixedSize(true)
        movieList.layoutManager = LinearLayoutManager(this.context)
        movieList.adapter = adapter
        progressBar = view.findViewById(R.id.progressBar)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            BaseViewModel::class.java
        )
        viewModel.getMovies().observe(this, Observer { item ->
            if (item != null) {
                adapter.setData(item as ArrayList<Movie>)
            }
            showLoading(false)
        })
        viewModel.setMovies("movie")
        adapter.setType("movie")
        showLoading(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}
