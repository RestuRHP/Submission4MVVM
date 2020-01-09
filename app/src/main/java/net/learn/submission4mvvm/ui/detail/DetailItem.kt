package net.learn.submission4mvvm.ui.detail

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detai_item.img_poster
import kotlinx.android.synthetic.main.activity_detai_item.tv_language
import kotlinx.android.synthetic.main.activity_detai_item.tv_overview
import kotlinx.android.synthetic.main.activity_detai_item.tv_rating
import kotlinx.android.synthetic.main.activity_detai_item.tv_release
import kotlinx.android.synthetic.main.activity_detai_item.tv_title
import net.learn.submission4mvvm.BuildConfig
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.db.DBFavorite
import net.learn.submission4mvvm.db.Helper
import net.learn.submission4mvvm.db.MappingHelper
import net.learn.submission4mvvm.model.movies.Movie

class DetailItem : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var helper:Helper

    private var fId = 0
    private var id = 0
    private var title = ""
    private var backdrop = ""
    private var language = ""
    private var poster = ""
    private var overview = ""
    private var release = ""
    private var rating = 0.0

    var menuItem:Menu?=null
    var isFavorite = false

    companion object {
        const val EX_FID = "extra_fid"
        const val EX_ID = "extra_id"
        const val EX_TITLE = "extra_movie"
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

        progressBar = findViewById(R.id.progressBar)

        helper=Helper(this)

        showLoading(true)

        val extras =intent.extras
        if (extras != null){
            showDetail(extras)
        }else{
            finish()
        }
        check()
    }

    private fun check(){
        helper.open()
        val cursor=helper.queryAll()
        val inDB : ArrayList<Movie> = MappingHelper.maping(cursor)
        Log.d("Check Data SQL","$inDB")
        Log.d("Check Data SQL","$fId")
        for (movie in inDB) {
            if (id == movie.id) {
                fId = movie.fId!!
                isFavorite = true
            }
            if (isFavorite) {
                break
            }
        }
        setFavorite()
        helper.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favorite_detail, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_add_fav) {
            if (isFavorite) {
                removeFavorite()
            } else {
                addToFavorite()
            }
            isFavorite = !isFavorite
            setFavorite()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun removeFavorite() {
        val fId = fId.toString()
        helper.open()
        helper.deleteById(fId)

        snackBar("$title Telah DiHapus Dari Favorite")

        helper.close()
    }

    private fun addToFavorite() {
        val values= ContentValues()
        values.put(DBFavorite.Colums.ID,id)
        values.put(DBFavorite.Colums.TITLE,title)
        values.put(DBFavorite.Colums.BACKDROP,backdrop)
        values.put(DBFavorite.Colums.ORIGINAL_LANG,language)
        values.put(DBFavorite.Colums.POSTER,poster)
        values.put(DBFavorite.Colums.OVERVIEW,overview)
        values.put(DBFavorite.Colums.RELEASE_DATE,release)
        values.put(DBFavorite.Colums.RATING,rating)

        helper.open()
        helper.insert(values)
        helper.close()

        snackBar("$title Telah Ditambahkan Ke Favorite")

        Log.d("Save Favorite : ","Success! $values")
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.setIcon(R.drawable.ic_favorite)
        } else {
            menuItem?.getItem(0)?.setIcon(R.drawable.ic_unfavorite)
        }
    }

    private fun showDetail(extras: Bundle) {
        fId = extras.getInt(EX_FID,0)
        id = extras.getInt(EX_ID,0)
        title = extras.getString(EX_TITLE,"")
        backdrop = extras.getString(EX_BACKDROP,"")
        language = extras.getString(EX_LANGUAGE,"")
        poster = extras.getString(EX_POSTER,"")
        overview = extras.getString(EX_OVERVIEW,"")
        release = extras.getString(EX_RELEASE,"")
        rating = extras.getDouble(EX_RATING, 0.0)

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

    private fun snackBar(message: String) {
        Snackbar.make(tv_title,message, Snackbar.LENGTH_SHORT).show()
    }
}
