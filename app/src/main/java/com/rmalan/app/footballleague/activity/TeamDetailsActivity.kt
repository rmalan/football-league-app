package com.rmalan.app.footballleague.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dicoding.picodiploma.myfootballclub.Team
import com.google.gson.Gson
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.R.color.colorAccent
import com.rmalan.app.footballleague.R.color.colorPrimaryDark
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.model.TeamDetails
import com.rmalan.app.footballleague.presenter.TeamDetailsPresenter
import com.rmalan.app.footballleague.util.invisible
import com.rmalan.app.footballleague.util.visible
import com.rmalan.app.footballleague.view.TeamDetailsView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamDetailsActivity : AppCompatActivity(), TeamDetailsView {

    companion object {
        const val EXTRA_TEAM_ID = "extra_team_id"
    }

    private lateinit var presenter: TeamDetailsPresenter
    private lateinit var team: Team
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    private lateinit var teamId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Team Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        teamId = intent.getStringExtra(EXTRA_TEAM_ID)

        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            teamBadge = imageView{}.lparams(height = dip(75))
                            teamName = textView {
                                this.gravity = Gravity.CENTER
                                textSize = 20f
                                textColor = ContextCompat.getColor(context, colorAccent)
                            }.lparams {
                                topMargin = dip(5)
                            }
                            teamFormedYear = textView{
                                this.gravity = Gravity.CENTER
                            }

                            teamStadium = textView{
                                this.gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, colorPrimaryDark)
                            }

                            teamDescription = textView().lparams{
                                topMargin = dip(20)
                            }
                        }

                        progressBar = progressBar {

                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }

//        FavoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailsPresenter(this, request, gson)
        presenter.getTeamDetail(teamId)

        swipeRefresh.onRefresh {
            presenter.getTeamDetail(teamId)
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamDetails(data: List<TeamDetails>) {
        team = Team(data[0].teamId, data[0].teamName, data[0].teamBadge)
        swipeRefresh.isRefreshing = false
        Picasso.get().load(data[0].teamBadge).into(teamBadge)
        teamName.text = data[0].teamName
        teamDescription.text = data[0].teamDescription
        teamFormedYear.text = data[0].teamFormedYear
        teamStadium.text = data[0].teamStadium
    }
}
