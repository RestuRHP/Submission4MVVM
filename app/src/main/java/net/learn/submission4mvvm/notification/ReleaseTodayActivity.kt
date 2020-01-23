package net.learn.submission4mvvm.notification

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_release_today.*
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.model.movies.Movie
import net.learn.submission4mvvm.ui.base.BaseAdapter
import net.learn.submission4mvvm.ui.base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*

class ReleaseTodayActivity : AppCompatActivity() {

    private lateinit var viewModel: BaseViewModel
    private lateinit var adapter: BaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release_today)

        supportActionBar!!.title = "Today's release"

        progressBar.visibility = View.VISIBLE
        if (intent.getSerializableExtra("list") != null) {
            loadList()
        }
//        loadList()
    }

    private fun loadList() {
        val todayDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        adapter = BaseAdapter()
        adapter.notifyDataSetChanged()
        rv_today_movies.setHasFixedSize(true)
        rv_today_movies.layoutManager = LinearLayoutManager(this)
        rv_today_movies.adapter = adapter
        viewModel = ViewModelProviders.of(this, ViewModelProvider.NewInstanceFactory())
            .get(BaseViewModel::class.java)
        viewModel.getMovies().observe(this, Observer { item ->
            if (item != null) {
                adapter.setData(item as ArrayList<Movie>)
            }
        })
        viewModel.releaseToday(dateFormat.format(todayDate), dateFormat.format(todayDate))
        adapter.setType("movie")
        progressBar.visibility = View.GONE
    }
}
