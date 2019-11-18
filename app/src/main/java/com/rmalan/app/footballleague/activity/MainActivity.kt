package com.rmalan.app.footballleague.activity

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.adapter.LeagueAdapter
import com.rmalan.app.footballleague.model.LeagueItems
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity() {

    private var leagueItems: MutableList<LeagueItems> = mutableListOf()
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
}
