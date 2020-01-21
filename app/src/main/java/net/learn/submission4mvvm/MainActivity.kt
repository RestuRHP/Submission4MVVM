package net.learn.submission4mvvm

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import net.learn.submission4mvvm.ui.favorite.FavoriteFragment
import net.learn.submission4mvvm.ui.movies.MoviesFragment
import net.learn.submission4mvvm.ui.search.SearchActivity
import net.learn.submission4mvvm.ui.tvshows.TvShowsFragment

class MainActivity : AppCompatActivity() {

    var content: Fragment = MoviesFragment()
    var title = "Movies"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
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
