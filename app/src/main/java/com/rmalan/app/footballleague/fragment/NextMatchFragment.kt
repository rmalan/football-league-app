package com.rmalan.app.footballleague.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.adapter.NextMatchAdapter
import com.rmalan.app.footballleague.adapter.PreviousMatchAdapter
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.model.Events
import com.rmalan.app.footballleague.preference.MyPreference
import com.rmalan.app.footballleague.presenter.NextMatchPresenter
import com.rmalan.app.footballleague.view.NextMatchlView
import kotlinx.android.synthetic.main.fragment_next_match.*
import kotlinx.android.synthetic.main.fragment_previous_match.*

class NextMatchFragment : Fragment(), NextMatchlView {

    private var nextMatch: MutableList<Events> = mutableListOf()
    private lateinit var presenter: NextMatchPresenter
    private lateinit var adapter: NextMatchAdapter
    private lateinit var rvNextMatch: RecyclerView
    private lateinit var preference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_next_match, container, false)
        setHasOptionsMenu(true)

        preference = MyPreference(this.activity!!)
        rvNextMatch = view.findViewById(R.id.rv_next_match)

        adapter = NextMatchAdapter(nextMatch)
        rvNextMatch.adapter = adapter
        rvNextMatch.setHasFixedSize(true)
        rvNextMatch.layoutManager = LinearLayoutManager(this.activity)

        val request = ApiRepository()
        val gson = Gson()
        presenter = NextMatchPresenter(this, request, gson)
        presenter.getNextMatch(preference.getLeagueId())

        return view
    }

    override fun showNextMatch(data: List<Events>) {
        nextMatch.clear()
        nextMatch.addAll(data)
        adapter.notifyDataSetChanged()
    }

}
