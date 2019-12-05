package com.rmalan.app.footballleague.activity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.adapter.LeagueAdapter
import com.rmalan.app.footballleague.adapter.MatchAdapter
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.model.Events
import com.rmalan.app.footballleague.model.LeagueItems
import com.rmalan.app.footballleague.presenter.SearchMatchPresenter
import com.rmalan.app.footballleague.view.SearchMatchlView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity(), SearchMatchlView {

    private var leagueItems: MutableList<LeagueItems> = mutableListOf()
    private var searchMatch: MutableList<Events> = mutableListOf()
    private lateinit var presenter: SearchMatchPresenter
    private lateinit var listLeague: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            padding = dip(8)

            relativeLayout {
                lparams(width = matchParent, height = wrapContent)

                listLeague = recyclerView {
                    layoutManager = LinearLayoutManager(context)
                    adapter =
                        LeagueAdapter(leagueItems) {
                            startActivity<LeagueDetailActivity>(
                                LeagueDetailActivity.EXTRA_LEAGUE_ID to it.id
                            )
                        }
                }
            }
        }

        showLeague()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                listLeague = recyclerView {
                    layoutManager = LinearLayoutManager(context)
                    adapter =
                        MatchAdapter(searchMatch) {
                            startActivity<EventDetailsActivity>(
                                EventDetailsActivity.EXTRA_EVENT_ID to it.eventId,
                                EventDetailsActivity.EXTRA_HOME_TEAM_ID to it.homeTeamId,
                                EventDetailsActivity.EXTRA_AWAY_TEAM_ID to it.awayTeamId
                            )
                        }
                }

                val request = ApiRepository()
                val gson = Gson()
                presenter = SearchMatchPresenter(this@MainActivity, request, gson)
                presenter.getSearchEvents(query)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    private fun showLeague() {
        val id = resources.getStringArray(R.array.league_id)
        val name = resources.getStringArray(R.array.league_name)
        val description = resources.getStringArray(R.array.description)
        val badge = resources.obtainTypedArray(R.array.league_badge)
        leagueItems.clear()
        for (i in name.indices) {
            leagueItems.add(
                LeagueItems(
                    id[i],
                    name[i],
                    description[i],
                    badge.getResourceId(i, 0)
                )
            )
        }

        badge.recycle()
    }

    override fun showSearchMatch(data: List<Events>) {
        searchMatch.clear()
        searchMatch.addAll(data)
    }
}
