package net.learn.submission4mvvm.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.model.movies.Movie
import net.learn.submission4mvvm.ui.base.BaseAdapter
import net.learn.submission4mvvm.ui.base.BaseViewModel
import java.util.*

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: BaseViewModel
    private lateinit var adapter: BaseAdapter
    private lateinit var fragment: MovieSearchFragment
    val EXTRA_QUERY = "extra_query"
    private var query: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar!!.title = ""

        adapter = BaseAdapter()
        viewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java)

        val arrayAdapter = ArrayAdapter<String>(
            applicationContext, android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.search_filter_array)
        )
        spinner_search_result.adapter = arrayAdapter
        spinner_search_result.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                progressBar_search_result.visibility = View.VISIBLE
                tv_not_found_search_result.visibility = View.GONE
                if (spinner_search_result.selectedItemPosition == 0) {
                    getListItem("movie", query)
                } else {
                    getListItem("tv", query)
                }
            }
        }
    }

    fun getListItem(type: String, text: String) {
        adapter = BaseAdapter()
        adapter.notifyDataSetChanged()
        rv_movies.setHasFixedSize(true)
        rv_movies.layoutManager = LinearLayoutManager(this)
        rv_movies.adapter = adapter
        viewModel = ViewModelProviders.of(this, ViewModelProvider.NewInstanceFactory())
            .get(BaseViewModel::class.java)
        viewModel.getMovies().observe(this, Observer { item ->
            if (item != null) {
                adapter.setData(item as ArrayList<Movie>)
            }
        })
        viewModel.searchMovie(type, 1, text)
        tv_not_found_search_result.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (menu?.findItem(R.id.act_search)?.actionView) as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
//                query = p0.toString()
//                progressBar_search_result.visibility = View.VISIBLE
//                if (spinner_search_result.selectedItemPosition == 0){
//                    getListItem("movie",query)
//                    adapter.setType("movie")
//                }else{
//                    getListItem("tv",query)
//                    adapter.setType("tv")
//                }

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                query = p0.toString()
//                progressBar_search_result.visibility = View.VISIBLE
                if (spinner_search_result.selectedItemPosition == 0) {
                    getListItem("movie", query)
                    adapter.setType("movie")
                } else {
                    getListItem("tv", query)
                    adapter.setType("tv")
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("query", query)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        query = savedInstanceState?.getString("query").toString()
    }

}
