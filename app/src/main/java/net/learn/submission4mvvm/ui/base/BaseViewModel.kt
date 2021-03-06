package net.learn.submission4mvvm.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.learn.submission4mvvm.BuildConfig
import net.learn.submission4mvvm.model.movies.Movie
import net.learn.submission4mvvm.objectdata.MovieObject
import net.learn.submission4mvvm.service.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseViewModel : ViewModel() {

    internal val listMovies = MutableLiveData<List<Movie>>()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(Api::class.java)
    fun setMovies(type: String, page: Int = 1) {
        GlobalScope.launch(Dispatchers.IO) {
            val movieObjectCall = api.getListItem(type = type, page = page)
            movieObjectCall.enqueue(object : Callback<MovieObject> {
                override fun onResponse(call: Call<MovieObject>, response: Response<MovieObject>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        listMovies.value = responseBody?.results
                        Log.d("Repository", "List Today :${responseBody?.results}")
                    }
                }

                override fun onFailure(call: Call<MovieObject>, t: Throwable) {
                    Log.d("setMovies Failure", "" + t.message)
                }
            })
        }
    }

    fun searchMovie(type: String, page: Int, query: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val movieObjectCall = api.getSearch(type = type, page = page, query = query)
            movieObjectCall.enqueue(object : Callback<MovieObject> {
                override fun onResponse(call: Call<MovieObject>, response: Response<MovieObject>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        listMovies.value = responseBody?.results
                        Log.d("Repository", "List Today :${responseBody?.results}")
                    }
                }

                override fun onFailure(call: Call<MovieObject>, t: Throwable) {
                    Log.d("searchMovie Failure", "" + t.message)
                }
            })
        }
    }

    fun releaseToday(firstDate: String, lastDate: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val movieObjectCall = api.getReleaseToday(firstDate = firstDate, lastDate = lastDate)
            movieObjectCall.enqueue(object : Callback<MovieObject> {
                override fun onResponse(call: Call<MovieObject>, response: Response<MovieObject>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        listMovies.value = responseBody?.results
                        Log.d("Repository", "List Today :${responseBody?.results}")
                    }
                }

                override fun onFailure(call: Call<MovieObject>, t: Throwable) {
                    Log.d("releaseToday Failure", "" + t.message)
                }
            })
        }
    }

    internal fun getMovies(): MutableLiveData<List<Movie>> {
        return listMovies
    }
}