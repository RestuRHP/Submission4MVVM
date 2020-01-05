package net.learn.submission4mvvm.ui.tvshows


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.model.tv.TvShows
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class TvShowsFragment : Fragment() {

    private lateinit var adapter: TvShowsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: TvShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_display, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TvShowsAdapter()
        adapter.notifyDataSetChanged()
        val tvlist: RecyclerView = view.findViewById(R.id.rv_movies)
        tvlist.setHasFixedSize(true)
        tvlist.layoutManager = LinearLayoutManager(this.context)
        tvlist.adapter=adapter
        progressBar = view.findViewById(R.id.progressBar)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TvShowsViewModel::class.java)
        viewModel.getTv().observe(this, Observer { item ->
            if (item != null) {
                adapter.setData(item as ArrayList<TvShows>)
            }
            showLoading(false)
        })
        viewModel.setTv()
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
