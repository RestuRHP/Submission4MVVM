package net.learn.submission4mvvm

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import net.learn.submission4mvvm.notification.Receiver
import net.learn.submission4mvvm.ui.favorite.FavoriteFragment
import net.learn.submission4mvvm.ui.movies.MoviesFragment
import net.learn.submission4mvvm.ui.search.SearchActivity
import net.learn.submission4mvvm.ui.setting.SettingActivity
import net.learn.submission4mvvm.ui.setting.SharedPrefManager
import net.learn.submission4mvvm.ui.tvshows.TvShowsFragment

class MainActivity : AppCompatActivity() {

    var content: Fragment = MoviesFragment()
    var title = "Movies"
    private val receiver = Receiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = SharedPrefManager(
            applicationContext
        ).getInstance(applicationContext)
        if (sharedPref.checkInit() == 0) {
            sharedPref.setDailyReminder(true)
            sharedPref.setReleaseReminder(true)
            receiver.setRepeatingAlarm(
                applicationContext, Receiver().TYPE_DAILY, "07:00",
                getString(R.string.daily_notif_message)
            )
            receiver.setRepeatingAlarm(
                applicationContext, Receiver().TYPE_RELEASE, "08:00",
                getString(R.string.release_notif_message)
            )
        }
        if (intent.extras != null) {
            val type = intent.getStringExtra("type")
            if (type == "release") {
                val intent = Intent(applicationContext, ReleaseTodayActivity::class.java)
                intent.putExtra("list", this.intent.getSerializableExtra("list"))
                startActivity(intent)
            }
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.navigation_movies
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.navigation_movies -> {
                    content = MoviesFragment()
                    title = getString(R.string.movies)
                    setActionBarTitle(title)
                    addFragment(content)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_tvshows -> {
                    content = TvShowsFragment()
                    title = getString(R.string.tvShows)
                    setActionBarTitle(title)
                    addFragment(content)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorite -> {
                    content = FavoriteFragment()
                    title = getString(R.string.favorite)
                    setActionBarTitle(title)
                    addFragment(content)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.layout_container, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            startActivity(Intent(applicationContext, SettingActivity::class.java))
        } else if (item.itemId == R.id.action_search) {
            val mIntent = Intent(this, SearchActivity::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("title", title)
        supportFragmentManager.putFragment(outState, "fragment", content)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        content = supportFragmentManager.getFragment(savedInstanceState, "fragment")!!
        title = savedInstanceState.getString("title", "")
        addFragment(content)
        setActionBarTitle(title)
    }
}
