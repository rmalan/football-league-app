package com.rmalan.app.footballleague.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.adapter.StandingsAdapter
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.model.Standings
import com.rmalan.app.footballleague.preference.MyPreference
import com.rmalan.app.footballleague.presenter.StandingsPresenter
import com.rmalan.app.footballleague.util.invisible
import com.rmalan.app.footballleague.util.visible
import com.rmalan.app.footballleague.view.StandingsView

class StandingsFragment : Fragment(), StandingsView {

    private var standings: MutableList<Standings> = mutableListOf()
    private lateinit var presenter: StandingsPresenter
    private lateinit var adapter: StandingsAdapter
    private lateinit var listStandings: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var preference: MyPreference

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar = view!!.findViewById(R.id.progress_bar_standings)
        listStandings = view!!.findViewById(R.id.rv_standings)
        preference = MyPreference(requireActivity())

        adapter = StandingsAdapter(standings)
        listStandings.adapter = adapter
        listStandings.setHasFixedSize(true)
        listStandings.layoutManager = LinearLayoutManager(this.activity)
        adapter.notifyDataSetChanged()

        val request = ApiRepository()
        val gson = Gson()
        presenter = StandingsPresenter(this, request, gson)
        presenter.getStandingList(preference.getLeagueId())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_standings, container, false)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showStandings(data: List<Standings>) {
        standings.clear()
        standings.addAll(data)
        adapter.notifyDataSetChanged()
    }

}
