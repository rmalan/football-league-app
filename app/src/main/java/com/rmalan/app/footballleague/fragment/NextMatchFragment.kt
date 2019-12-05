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
import com.rmalan.app.footballleague.activity.EventDetailsActivity
import com.rmalan.app.footballleague.adapter.MatchAdapter
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.model.Events
import com.rmalan.app.footballleague.preference.MyPreference
import com.rmalan.app.footballleague.presenter.NextMatchPresenter
import com.rmalan.app.footballleague.util.invisible
import com.rmalan.app.footballleague.util.visible
import com.rmalan.app.footballleague.view.NextMatchlView
import org.jetbrains.anko.support.v4.startActivity

class NextMatchFragment : Fragment(), NextMatchlView {

    private var nextMatch: MutableList<Events> = mutableListOf()
    private lateinit var presenter: NextMatchPresenter
    private lateinit var adapter: MatchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var rvNextMatch: RecyclerView
    private lateinit var preference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_next_match, container, false)

        progressBar = view.findViewById(R.id.progress_bar_next)

        preference = MyPreference(this.activity!!)
        rvNextMatch = view.findViewById(R.id.rv_next_match)

        adapter = MatchAdapter(nextMatch) {
            startActivity<EventDetailsActivity>(
                EventDetailsActivity.EXTRA_EVENT_ID to it.eventId,
                EventDetailsActivity.EXTRA_HOME_TEAM_ID to it.homeTeamId,
                EventDetailsActivity.EXTRA_AWAY_TEAM_ID to it.awayTeamId
            )
        }
        rvNextMatch.adapter = adapter
        rvNextMatch.setHasFixedSize(true)
        rvNextMatch.layoutManager = LinearLayoutManager(this.activity)

        val request = ApiRepository()
        val gson = Gson()
        presenter = NextMatchPresenter(this, request, gson)
        presenter.getNextMatch(preference.getLeagueId())

        return view
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showNextMatch(data: List<Events>) {
        nextMatch.clear()
        nextMatch.addAll(data)
        adapter.notifyDataSetChanged()
    }

}
