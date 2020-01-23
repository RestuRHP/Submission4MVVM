package net.learn.submission4mvvm.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import net.learn.submission4mvvm.BuildConfig
import net.learn.submission4mvvm.MainActivity
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.model.movies.Movie
import net.learn.submission4mvvm.objectdata.MovieObject
import net.learn.submission4mvvm.service.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Receiver : BroadcastReceiver() {

    val TYPE_DAILY = "daily"
    val TYPE_RELEASE = "release"
    val EXTRA_MESSAGE = "message"
    val EXTRA_TYPE = "type"
    var listMovies = ArrayList<Movie>()

    private val ID_DAILY = 101
    private val ID_RELEASE = 101

    private val TIME_FORMAT = "HH:mm"

    lateinit var title: String
    lateinit var message: String
    lateinit var type: String
    var notifId = 0

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(Api::class.java)

    override fun onReceive(p0: Context?, p1: Intent?) {
        type = p1?.getStringExtra(EXTRA_TYPE) as String
        message = p1.getStringExtra(EXTRA_MESSAGE) as String
        title = if (type.equals(TYPE_DAILY, ignoreCase = true)) TYPE_DAILY else TYPE_RELEASE
        notifId = if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE
        if (type == TYPE_RELEASE) {
            getReleaseToday(p0)
            Log.d("Release Execute", ": Yes")
        } else if (type == TYPE_DAILY) {
            showAlarmNotification(p0 as Context, title, message, notifId, type, arrayListOf())
        }
        Toast.makeText(p0, "$title : $message", Toast.LENGTH_LONG).show()
    }

    private fun getReleaseToday(context: Context?) {
        val todayDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val call = api.getReleaseToday(
            firstDate = dateFormat.format(todayDate),
            lastDate = dateFormat.format(todayDate)
        )
        call.enqueue(object : Callback<MovieObject> {
            override fun onResponse(call: Call<MovieObject>, response: Response<MovieObject>) {
                val responseBody = response.body()?.results!!
                listMovies.addAll(responseBody)
                if (listMovies.size > 0) {
                    showAlarmNotification(
                        context as Context,
                        title,
                        message,
                        notifId,
                        type,
                        listMovies
                    )
                    Log.d("getReleaseToday ", ":${listMovies}")
                }
            }

            override fun onFailure(call: Call<MovieObject>, t: Throwable) {
                Log.d("Response Failure", "" + t.message)
            }
        })
    }

    fun setRepeatingAlarm(context: Context, type: String, time: String, message: String) {
        if (isDateInvalid(time, TIME_FORMAT)) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Receiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE,
            intent,
            0
        )
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(
            context,
            context.getString(R.string.toast_reminder_enabled, type),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showAlarmNotification(
        context: Context, title: String, message: String, notifId: Int, type: String,
        movieList: ArrayList<Movie>
    ) {
        val CHANNEL_ID = context.getString(R.string.default_channel_id)
        val CHANNEL_NAME = context.getString(R.string.default_channel_name)
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("type", type)
        if (type == TYPE_RELEASE) {
            intent.putExtra("list", movieList)
            Log.d("tes123", "list show = $movieList")
        }
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            notificationBuilder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = notificationBuilder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    private fun isDateInvalid(date: String, format: String): Boolean {
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }

    }

    fun cancelAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Receiver::class.java)
        val requestCode = if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(
            context,
            context.getString(R.string.toast_reminder_disabled, type),
            Toast.LENGTH_SHORT
        ).show()

    }

}