package net.learn.submission4mvvm.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detai_item.*
import net.learn.submission4mvvm.BuildConfig
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.model.detail.Detail

class DetailItem : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: DetailItemViewModel
    private var id = 0

    companion object {
        const val EX_TITLE = "extra_movie"
        const val EX_RELEASE = "ex_Release"
        const val EX_RATING = "ex_rating"
        const val EX_LANGUAGE = "ex_language"
        const val EX_OVERVIEW = "ex_overview"
        const val EX_BACKDROP = "ex_backdrop"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detai_item)

        progressBar = findViewById(R.id.progressBar)
//        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(DetailItemViewModel::class.java)
////        viewModel.getDetailItem.observe(this,getDetail)
//        viewModel.getDetailItem().observe(this, Observer { item ->
//            if (item != null) {
//                showDetail(item)
//                Log.d("Item", ": $item")
//                showLoading(false)
//            }
//        })
//        id = intent.getIntExtra("id", id)
//        Log.d("Check Id", "Movie Id $id")
//        viewModel.setDetailMovies(id)
        val extras =intent.extras
        if (extras != null){
            showLoading(true)
            showDetail(extras)
        }else{
            finish()
        }
    }

    private fun showDetail(extras: Bundle) {
        tv_title.text = extras.getString(EX_TITLE,"")
        tv_release.text = extras.getString(EX_RELEASE,"")
        tv_rating.text = extras.getDouble(EX_RATING,0.0).toString()
        tv_language.text = extras.getString(EX_LANGUAGE,"")
        tv_overview.text = extras.getString(EX_OVERVIEW,"")
        Glide.with(this)
            .load(BuildConfig.BACKDROP_PATH + extras.getString(EX_BACKDROP))
            .placeholder(R.color.colorAccent)
            .dontAnimate()
            .into(img_poster)
        showLoading(false)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
