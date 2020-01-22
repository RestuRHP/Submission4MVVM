package net.learn.submission4mvvm.ui.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import net.learn.submission4mvvm.BuildConfig
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.db.Helper
import net.learn.submission4mvvm.model.movies.Movie


class StackRemoteViewsFactory(val context: Context) : RemoteViewsService.RemoteViewsFactory {
    private val widgetItems = ArrayList<Movie>()
    lateinit var helper: Helper

    override fun onCreate() {

    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {

        val identityToken = Binder.clearCallingIdentity()
        helper = Helper(context)
        helper.open()
        widgetItems.clear()
        widgetItems.addAll(helper.queryAllWidget())
        Log.d("TES123", "fav movies size" + widgetItems.size.toString())
        Binder.restoreCallingIdentity(identityToken)
    }

    fun loadWidget(){
        onDataSetChanged()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        val poster = Glide.with(context)
            .asBitmap()
            .load(BuildConfig.POSTER_PATH + widgetItems[position].posterPath)
            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .get()

        rv.setImageViewBitmap(R.id.imageView, poster)
        val extras = Bundle()
        extras.putInt(WidgetFavorite().EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getCount(): Int {
        return widgetItems.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {

    }

}