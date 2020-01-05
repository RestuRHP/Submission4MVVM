package net.learn.submission4mvvm.ui.tvshows

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.learn.submission4mvvm.BuildConfig
import net.learn.submission4mvvm.model.tv.TvShows
import net.learn.submission4mvvm.objectdata.TvShowsObject
import net.learn.submission4mvvm.service.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TvShowsViewModel : ViewModel() {
    private val listTv = MutableLiveData<List<TvShows>>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(Api::class.java)

    fun setTv(page:Int=1){
        val tvObjectCall = api.getTvList(page=page)
        tvObjectCall.enqueue(object : Callback<TvShowsObject> {
            override fun onResponse(call: Call<TvShowsObject>, response: Response<TvShowsObject>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    listTv.setValue(responseBody?.results)
                    Log.d("Repository", " List Movie : ${responseBody?.results}")
                }
            }

            override fun onFailure(call: Call<TvShowsObject>, t: Throwable) {
                Log.d("Response Failure",""+t.message)
            }
        })
    }

    internal fun getTv(): MutableLiveData<List<TvShows>> {
        return listTv
    }
}