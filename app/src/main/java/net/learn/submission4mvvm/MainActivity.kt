package net.learn.submission4mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import net.learn.submission4mvvm.ui.favorite.FavoriteFragment
import net.learn.submission4mvvm.ui.movies.MoviesFragment
import net.learn.submission4mvvm.ui.tvshows.TvShowsFragment

class MainActivity : AppCompatActivity() {

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
                setActionBarTitle(getString(R.string.movies))
                addFragment(MoviesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_tvshows ->{
//                val title="Tv Shows"
                setActionBarTitle(getString(R.string.tvShows))
                addFragment(TvShowsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite ->{
//                val title="Movies"
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
}
