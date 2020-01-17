package net.learn.submission4mvvm.ui.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.favorite_fragment.*
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.ui.favorite.tab.FavoriteMovies
import net.learn.submission4mvvm.ui.favorite.tab.FavoriteMoviesProvider
import net.learn.submission4mvvm.ui.favorite.tab.FavoriteTvShows

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.favorite_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoritePagerAdapter(this.childFragmentManager)
        adapter.addFragment(FavoriteMoviesProvider(), getString(R.string.movies))
        adapter.addFragment(FavoriteTvShows(), getString(R.string.tvShows))

        view_pager_favorite.adapter = adapter
        tab_layout_favorite.setupWithViewPager(view_pager_favorite)
    }
}
