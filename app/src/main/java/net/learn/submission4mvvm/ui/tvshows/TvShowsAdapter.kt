package net.learn.submission4mvvm.ui.tvshows

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*
import net.learn.submission4mvvm.BuildConfig
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.model.movies.Movie
import net.learn.submission4mvvm.model.tv.TvShows
import net.learn.submission4mvvm.ui.detail.DetailItem
import net.learn.submission4mvvm.ui.detail.DetailItem.Companion.EX_BACKDROP
import net.learn.submission4mvvm.ui.detail.DetailItem.Companion.EX_LANGUAGE
import net.learn.submission4mvvm.ui.detail.DetailItem.Companion.EX_OVERVIEW
import net.learn.submission4mvvm.ui.detail.DetailItem.Companion.EX_RATING
import net.learn.submission4mvvm.ui.detail.DetailItem.Companion.EX_RELEASE
import net.learn.submission4mvvm.ui.detail.DetailItem.Companion.EX_TITLE
import java.util.ArrayList

class TvShowsAdapter : RecyclerView.Adapter<TvShowsAdapter.Holder>() {
    private val tvData = ArrayList<TvShows>()
    fun setData(item: ArrayList<TvShows>) {
        tvData.clear()
        tvData.addAll(item)
        notifyDataSetChanged()
    }
    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(tv:TvShows){
            with(itemView){
                tv_title.text = tv.originalName
                tv_language.text=tv.originalLanguage
                tv_release.text=tv.firstAirDate
                tv_overview.text=tv.overview
                tv_rating.text=checkTextIfNull(""+tv.voteAverage)
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_PATH+tv.posterPath)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(img_poster)
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context,DetailItem::class.java)
                    intent.putExtra(EX_TITLE,tv.originalName)
                    intent.putExtra(EX_RELEASE,tv.firstAirDate)
                    intent.putExtra(EX_RATING,tv.voteAverage)
                    intent.putExtra(EX_LANGUAGE,tv.originalLanguage)
                    intent.putExtra(EX_OVERVIEW,tv.overview)
                    intent.putExtra(EX_BACKDROP,tv.backdropPath)
                    itemView.context.startActivity(intent)
                }
            }
        }
        fun checkTextIfNull(text: String?): String {
            return if (text != null && !text.isEmpty()) {
                text
            } else {
                "-"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = tvData.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(tvData[position])
    }

}