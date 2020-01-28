package net.learn.submission4mvvm.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import net.learn.submission4mvvm.R

/**
 * Implementation of App Widget functionality.
 */
class WidgetFavorite : AppWidgetProvider() {

    val toast = "TOAST_ACTION"
    val exItem = "EXTRA_ITEM"

    companion object {
        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val views = RemoteViews(context.packageName, R.layout.widget_banner)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(
                R.id.stack_view,
                R.id.empty_view
            )
            val toastIntent = Intent(context, WidgetFavorite::class.java)
            toastIntent.action = WidgetFavorite().toast
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val toastPendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    0,
                    toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
//        if (intent?.action != null) {
//            if (intent.action == toast) {
//                val viewIndex = intent.getIntExtra(exItem, 0)
//                Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
//
//                val man = AppWidgetManager.getInstance(context)
//                val ids = man.getAppWidgetIds(ComponentName(context!!, WidgetFavorite::class.java))
//                val updateIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
//                updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ids)
//                man.notifyAppWidgetViewDataChanged(ids, R.id.stack_view)
//            }
//        }
        if (intent!!.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context!!, WidgetFavorite::class.java)
            val id = appWidgetManager.getAppWidgetIds(thisWidget)
            appWidgetManager.notifyAppWidgetViewDataChanged(id, R.id.stack_view)
        }

    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}