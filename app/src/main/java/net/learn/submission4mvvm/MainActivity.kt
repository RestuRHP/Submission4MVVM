package net.learn.submission4mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import net.learn.submission4mvvm.ui.favorite.FavoriteFragment
import net.learn.submission4mvvm.ui.movies.MoviesFragment
import net.learn.submission4mvvm.ui.tvshows.TvShowsFragment

class MainActivity : AppCompatActivity() {

    var fragment = "Movies"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) bottomNavigationView.selectedItemId = R.id.navigation_movies
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem: MenuItem ->
        when (menuItem.itemId) {
            R.id.navigation_movies -> {
//                val title="Movies"
                fragment = "Movies"
                setActionBarTitle(getString(R.string.movies))
                addFragment(MoviesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_tvshows ->{
//                val title="Tv Shows"
                fragment = "Tv Shows"
                setActionBarTitle(getString(R.string.tvShows))
                addFragment(TvShowsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite ->{
//                val title="Movies"
                fragment = "Favorite"
                setActionBarTitle(getString(R.string.favorite))
                addFragment(FavoriteFragment())
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
    private fun setActionBarTitle(title: String){
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("Fragment", fragment)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        fragment = savedInstanceState.getString("Fragment").toString()

        if(fragment == "Movies"){
            addFragment(MoviesFragment())
            bottomNavigationView.selectedItemId = R.id.navigation_movies
        }
        else if(fragment == "Tv Shows"){
            addFragment(TvShowsFragment())
            bottomNavigationView.selectedItemId = R.id.navigation_tvshows

        }
        else if(fragment == "Tv Shows"){
            addFragment(FavoriteFragment())
            bottomNavigationView.selectedItemId = R.id.navigation_favorite

        }
    }
}
