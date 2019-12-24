package com.rmalan.app.footballleague.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.fragment.NextMatchFragment
import com.rmalan.app.footballleague.fragment.PreviousMatchFragment
import com.rmalan.app.footballleague.fragment.StandingsFragment
import com.rmalan.app.footballleague.fragment.TeamsFragment

class SectionsPagerAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(
        R.string.tab_previous,
        R.string.tab_next,
        R.string.tab_standings,
        R.string.tab_team
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = PreviousMatchFragment()
            1 -> fragment = NextMatchFragment()
            2 -> fragment = StandingsFragment()
            3 -> fragment = TeamsFragment()
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int {
        return 4
    }
}