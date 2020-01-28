package net.learn.favoritemovie.ui.detail

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detai_item.*
import net.learn.favoritemovie.BuildConfig
import net.learn.favoritemovie.R

class DetailItem : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private var item: ClipData.Item? = null

    private var fId = 0
    private var fType = ""
    private var id = 0
    private var title = ""
    private var backdrop = ""
    private var language = ""
    private var poster = ""
    private var overview = ""
    private var release = ""
    private var rating = 0.0

    companion object {
        const val EX_FID = "ex_fid"
        const val EX_TYPE = "ex_type"
        const val EX_ID = "ex_id"
        const val EX_TITLE = "ex_title"
        const val EX_RELEASE = "ex_Release"
        const val EX_RATING = "ex_rating"
        const val EX_LANGUAGE = "ex_language"
        const val EX_OVERVIEW = "ex_overview"
        const val EX_BACKDROP = "ex_backdrop"
        const val EX_POSTER = "ex_poster"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detai_item)

        supportActionBar!!.title = intent.getStringExtra(EX_TITLE)

        progressBar = findViewById(R.id.progressBar)


        showLoading(true)

        val extras = intent.extras
        if (extras != null) {
            showDetail(extras)
        } else {
            finish()
        }
    }


    private fun showDetail(extras: Bundle) {
        fId = extras.getInt(EX_FID, 0)
        fType = extras.getString(EX_TYPE, "")
        id = extras.getInt(EX_ID, 0)
        Log.d("get Intent ID :", "$id")
        title = extras.getString(EX_TITLE, "")
        backdrop = extras.getString(EX_BACKDROP, "")
        language = extras.getString(EX_LANGUAGE, "")
        poster = extras.getString(EX_POSTER, "")
        overview = extras.getString(EX_OVERVIEW, "")
        release = extras.getString(EX_RELEASE, "")
        rating = extras.getDouble(EX_RATING, 0.0)

        tv_title.text = extras.getString(EX_TITLE, "")
        tv_release.text = extras.getString(EX_RELEASE, "")
        tv_rating.text = extras.getDouble(EX_RATING, 0.0).toString()
        tv_language.text = extras.getString(EX_LANGUAGE, "")
        tv_overview.text = extras.getString(EX_OVERVIEW, "")
        Glide.with(this)
            .load(BuildConfig.BACKDROP_PATH + extras.getString(EX_BACKDROP))
            .placeholder(R.color.colorAccent)
            .dontAnimate()
            .into(img_poster)
        showLoading(false)
        Log.d("Get Type Fragment", fType)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
