package com.rmalan.app.footballleague.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rmalan.app.footballleague.R
import kotlinx.android.synthetic.main.activity_home.*
import com.rmalan.app.footballleague.R.id.leagues
import com.rmalan.app.footballleague.R.id.favorites
import com.rmalan.app.footballleague.fragment.FavoriteMatchesFragment
import com.rmalan.app.footballleague.fragment.LeaguesFragment

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
                    loadFavoriteMatchesFragment(savedInstanceState)
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
                .replace(R.id.main_container, LeaguesFragment(), LeaguesFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoriteMatchesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, FavoriteMatchesFragment(), FavoriteMatchesFragment::class.java.simpleName)
                .commit()
        }
    }
}
