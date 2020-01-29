package net.learn.submission4mvvm.ui.base

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
import net.learn.submission4mvvm.ui.detail.DetailItemActivity

class BaseFavoriteAdapter : RecyclerView.Adapter<BaseFavoriteAdapter.Holder>() {

    var listFavorite = ArrayList<Movie>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)
            notifyDataSetChanged()
        }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movies: Movie) {
            with(itemView) {
                tv_title.text = movies.title
                tv_language.text = movies.originalLanguage
                tv_rating.text = checkTextIfNull("" + movies.voteAverage)
                tv_overview.text = movies.overview
                tv_release.text = movies.releaseDate
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_PATH + movies.posterPath)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(img_poster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailItemActivity::class.java)
                    intent.putExtra(DetailItemActivity.EX_FID, movies.fId)
                    intent.putExtra(DetailItemActivity.EX_ID, movies.id)
                    intent.putExtra(DetailItemActivity.EX_TITLE, movies.title)
                    intent.putExtra(DetailItemActivity.EX_RELEASE, movies.releaseDate)
                    intent.putExtra(DetailItemActivity.EX_RATING, movies.voteAverage)
                    intent.putExtra(DetailItemActivity.EX_LANGUAGE, movies.originalLanguage)
                    intent.putExtra(DetailItemActivity.EX_OVERVIEW, movies.overview)
                    intent.putExtra(DetailItemActivity.EX_BACKDROP, movies.backdropPath)
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = this.listFavorite.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(listFavorite[position])
    }
}