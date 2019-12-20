package com.rmalan.app.footballleague.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.fragment.*

class SectionsPagerFavoriteAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(R.string.tab_match, R.string.tab_team)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FavoriteMatchesFragment()
            1 -> fragment = FavoriteTeamsFragment()
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int {
        return 2
    }
}