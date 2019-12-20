package com.rmalan.app.footballleague.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.adapter.SectionsPagerFavoriteAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val sectionPagerFavoriteAdapter = SectionsPagerFavoriteAdapter(requireActivity(), childFragmentManager)
        view_pager_favorite.adapter = sectionPagerFavoriteAdapter
        tabs_favorite.setupWithViewPager(view_pager_favorite)

        super.onActivityCreated(savedInstanceState)
    }
}
