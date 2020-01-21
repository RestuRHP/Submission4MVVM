package net.learn.submission4mvvm.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.learn.submission4mvvm.BuildConfig
import net.learn.submission4mvvm.model.detail.Detail
import net.learn.submission4mvvm.service.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailItemViewModel : ViewModel() {
    private val itemDetail = MutableLiveData<Detail>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(Api::class.java)
    fun setDetailMovies(id: Int) {
        val itemDetailCall = api.getMovieDetail(id)
        itemDetailCall.enqueue(object : Callback<Detail> {
            override fun onResponse(call: Call<Detail>?, response: Response<Detail>?) {
                val responseBody = response?.body()
                itemDetail.value = responseBody
                Log.d("Detail", " Movie : $responseBody")
            }

            override fun onFailure(call: Call<Detail>, t: Throwable) {
                Log.w("Response Detail Failed", "Show message" + t.message)
            }
        })
    }

    //    internal fun getDetailItem():MutableLiveData<Detail>{
//        return itemDetail
//    }
    val getDetailItem: LiveData<Detail>
        get() = itemDetail

}