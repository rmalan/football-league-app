package com.rmalan.app.footballleague

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class LeagueDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LEAGUE_ITEM = "extra_league_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val leagueItem = intent.getParcelableExtra<LeagueItems>(EXTRA_LEAGUE_ITEM)
        LeagueDetailActivityUI(leagueItem).setContentView(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    inner class LeagueDetailActivityUI(val leagueItem: LeagueItems) :
        AnkoComponent<LeagueDetailActivity> {
        val viewId = 1
        val badgeId = 2
        val nameId = 3

        override fun createView(ui: AnkoContext<LeagueDetailActivity>) = with(ui) {
            scrollView {
                lparams(matchParent, wrapContent)

                relativeLayout {
                    lparams(matchParent, wrapContent)

                    view {
                        id = viewId
                        setBackgroundColor(Color.rgb(0, 133, 119))
                    }.lparams(matchParent, dip(150))

                    imageView {
                        id = badgeId
                        leagueItem.badge?.let {
                            Picasso.get().load(leagueItem.badge).fit().into(this)
                        }
                    }.lparams(dip(100), dip(100)) {
                        centerHorizontally()
                        topMargin = dip(100)
                    }

                    textView {
                        id = nameId
                        text = leagueItem.name
                        textSize = 24f
                        setTypeface(null, Typeface.BOLD)
                    }.lparams {
                        below(badgeId)
                        centerHorizontally()
                    }

                    textView {
                        padding = dip(16)
                        text = leagueItem.description
                    }.lparams {
                        below(nameId)
                    }
                }
            }
        }
    }
}
