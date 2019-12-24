package com.rmalan.app.footballleague.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.R.id.favorites
import com.rmalan.app.footballleague.R.id.leagues
import com.rmalan.app.footballleague.fragment.FavoriteFragment
import com.rmalan.app.footballleague.fragment.LeaguesFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                leagues -> {
                    loadLeaguesFragment(savedInstanceState)
                }
                favorites -> {
                    loadFavoriteFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = leagues
    }

    private fun loadLeaguesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    LeaguesFragment(),
                    LeaguesFragment::class.java.simpleName
                )
                .commit()
        }
    }

    private fun loadFavoriteFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    FavoriteFragment(),
                    FavoriteFragment::class.java.simpleName
                )
                .commit()
        }
    }
}
