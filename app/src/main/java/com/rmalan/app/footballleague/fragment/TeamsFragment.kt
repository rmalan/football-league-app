package com.rmalan.app.footballleague.fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dicoding.picodiploma.myfootballclub.Team
import com.google.gson.Gson
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.activity.TeamDetailsActivity
import com.rmalan.app.footballleague.adapter.TeamsAdapter
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.preference.MyPreference
import com.rmalan.app.footballleague.presenter.TeamsPresenter
import com.rmalan.app.footballleague.util.invisible
import com.rmalan.app.footballleague.util.visible
import com.rmalan.app.footballleague.view.TeamsView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamsFragment : Fragment(), AnkoComponent<Context>, TeamsView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamsAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var preference: MyPreference

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preference = MyPreference(requireActivity())

        adapter = TeamsAdapter(teams) {
            context?.startActivity<TeamDetailsActivity>(
                TeamDetailsActivity.EXTRA_TEAM_ID to it.teamId
            )
        }
        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)
        presenter.getTeamList(preference.getLeagueId())

        swipeRefreshLayout.onRefresh {
            presenter.getTeamList(preference.getLeagueId())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.Companion.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefreshLayout = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_orange_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        id = R.id.list_team
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context)
                    }

                    progressBar = progressBar {

                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefreshLayout.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
