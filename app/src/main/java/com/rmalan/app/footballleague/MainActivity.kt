package com.rmalan.app.footballleague

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.rmalan.app.footballleague.adapter.LeagueAdapter
import com.rmalan.app.footballleague.entity.LeagueItems
import com.rmalan.app.footballleague.ui.LeagueDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity() {

    private var leagueItems: MutableList<LeagueItems> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI(leagueItems).setContentView(this)

        initData()
    }

    inner class MainActivityUI(val leagueItems: List<LeagueItems>) : AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            verticalLayout() {
                lparams(width = matchParent, height = wrapContent)

                recyclerView {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter =
                        LeagueAdapter(leagueItems) {
                            startActivity<LeagueDetailActivity>(LeagueDetailActivity.EXTRA_LEAGUE_ITEM to it)
                        }
                }
            }
        }
    }

    private fun initData() {
        val name = resources.getStringArray(R.array.league_name)
        val description = resources.getStringArray(R.array.description)
        val badge = resources.obtainTypedArray(R.array.league_badge)
        leagueItems.clear()
        for (i in name.indices) {
            leagueItems.add(
                LeagueItems(
                    name[i],
                    description[i],
                    badge.getResourceId(i, 0)
                )
            )
        }

        badge.recycle()
    }
}
