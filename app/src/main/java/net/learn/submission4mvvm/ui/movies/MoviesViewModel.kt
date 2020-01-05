package net.learn.submission4mvvm.ui.movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.learn.submission4mvvm.BuildConfig
import net.learn.submission4mvvm.objectdata.MovieObject
import net.learn.submission4mvvm.model.movies.Movie
import net.learn.submission4mvvm.service.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesViewModel:ViewModel(){
    private val listMovies = MutableLiveData<List<Movie>>()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(Api::class.java)
    fun setMovies(page:Int=1){
        val movieObjectCall = api.getMovieList(page=page)
        movieObjectCall.enqueue(object : Callback<MovieObject>{
            override fun onResponse(call: Call<MovieObject>, response: Response<MovieObject>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    listMovies.setValue(responseBody?.results)
                    Log.d("Repository", " List Movie : ${response.body()?.results}")
                }
            }

            override fun onFailure(call: Call<MovieObject>, t: Throwable) {
                Log.d("Response Failure",""+t.message)
            }
        })
    }

    internal fun getMovies(): MutableLiveData<List<Movie>> {
        return listMovies
    }
}