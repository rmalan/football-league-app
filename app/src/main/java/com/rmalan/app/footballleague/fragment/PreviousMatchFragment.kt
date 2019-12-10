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
import com.rmalan.app.footballleague.presenter.PreviousMatchPresenter
import com.rmalan.app.footballleague.util.invisible
import com.rmalan.app.footballleague.util.visible
import com.rmalan.app.footballleague.view.PreviousMatchlView
import org.jetbrains.anko.support.v4.startActivity

class PreviousMatchFragment : Fragment(), PreviousMatchlView {

    private var prevMatch: MutableList<Events> = mutableListOf()
    private lateinit var presenter: PreviousMatchPresenter
    private lateinit var adapter: MatchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var rvPrevMatch: RecyclerView
    private lateinit var preference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_previous_match, container, false)

        progressBar = view.findViewById(R.id.progress_bar_prev)

        preference = MyPreference(requireActivity())
        rvPrevMatch = view.findViewById(R.id.rv_prev_match)

        adapter = MatchAdapter(prevMatch) {
            startActivity<EventDetailsActivity>(
                EventDetailsActivity.EXTRA_EVENT_ID to it.eventId,
                EventDetailsActivity.EXTRA_HOME_TEAM_ID to it.homeTeamId,
                EventDetailsActivity.EXTRA_AWAY_TEAM_ID to it.awayTeamId
            )
        }
        rvPrevMatch.adapter = adapter
        rvPrevMatch.setHasFixedSize(true)
        rvPrevMatch.layoutManager = LinearLayoutManager(this.activity)

        val request = ApiRepository()
        val gson = Gson()
        presenter = PreviousMatchPresenter(this, request, gson)
        presenter.getPrevMatch(preference.getLeagueId())

        return view
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPrevMatch(data: List<Events>) {
        prevMatch.clear()
        prevMatch.addAll(data)
        adapter.notifyDataSetChanged()
    }

}
