package com.rmalan.app.footballleague.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.db.database
import com.rmalan.app.footballleague.model.EventDetails
import com.rmalan.app.footballleague.model.Events
import com.rmalan.app.footballleague.model.Favorite
import com.rmalan.app.footballleague.model.TeamDetails
import com.rmalan.app.footballleague.presenter.EventDetailsPresenter
import com.rmalan.app.footballleague.util.invisible
import com.rmalan.app.footballleague.util.visible
import com.rmalan.app.footballleague.view.EventDetailsView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.text.ParseException
import java.text.SimpleDateFormat

class EventDetailsActivity : AppCompatActivity(), EventDetailsView {

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
        const val EXTRA_HOME_TEAM_ID = "extra_home_team_id"
        const val EXTRA_AWAY_TEAM_ID = "extra_sesy_team_id"
    }

    private lateinit var event: Events

    private lateinit var presenter: EventDetailsPresenter
    private lateinit var progressBar: ProgressBar

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var eventId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        eventId = intent.getStringExtra(EXTRA_EVENT_ID)
        val homeTeamId = intent.getStringExtra(EXTRA_HOME_TEAM_ID)
        val awayTeamId = intent.getStringExtra(EXTRA_AWAY_TEAM_ID)

        progressBar = findViewById(R.id.progress_bar_event)

        favoriteState()

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventDetailsPresenter(this, request, gson)
        presenter.getEventDetails(eventId, homeTeamId, awayTeamId)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEVentDetails(
        eventDetails: List<EventDetails>,
        homeTeamDetails: List<TeamDetails>,
        awayTeamDetails: List<TeamDetails>
    ) {
        event = Events(
            eventDetails[0].eventId,
            null,
            homeTeamDetails[0].teamId,
            awayTeamDetails[0].teamId,
            eventDetails[0].homeTeam,
            eventDetails[0].awayTeam,
            eventDetails[0].homeScore,
            eventDetails[0].awayScore,
            eventDetails[0].dateEvent
        )
        league_name.text = eventDetails[0].leaggueName

        val dateMatch: String? = eventDetails[0].dateEvent
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = simpleDateFormat.parse(dateMatch)
            val newFormat = SimpleDateFormat("EE, MM/dd")
            val dateFix = newFormat.format(date)
            date_event.text = dateFix
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        Picasso.get().load(homeTeamDetails[0].teamBadge).into(img_home_team)
        Picasso.get().load(awayTeamDetails[0].teamBadge).into(img_away_team)
        home_team.text = eventDetails[0].homeTeam
        away_team.text = eventDetails[0].awayTeam
        home_score.text = eventDetails[0].homeScore
        away_score.text = eventDetails[0].awayScore
        home_goal.text = eventDetails[0].homeGoal
        away_goal.text = eventDetails[0].awayGoal
        home_shot.text = eventDetails[0].homeshots
        away_shot.text = eventDetails[0].awayShots
        home_yellow.text = eventDetails[0].homeYellow
        away_yellow.text = eventDetails[0].awayYellow
        home_red.text = eventDetails[0].homeRed
        away_red.text = eventDetails[0].awayRed
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (::event.isInitialized) {
                    if (isFavorite) removeFromFavorite() else addToFavorite()
                    isFavorite = !isFavorite
                    setFavorite()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please wait, loading data...",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_ID to event.eventId,
                    Favorite.HOME_TEAM_ID to event.homeTeamId,
                    Favorite.AWAY_TEAM_ID to event.awayTeamId,
                    Favorite.HOME_TEAM to event.homeTeam,
                    Favorite.AWAY_TEAM to event.awayTeam,
                    Favorite.HOME_SCORE to event.homeScore,
                    Favorite.AWAY_SCORE to event.awayScore,
                    Favorite.DATE_EVENT to event.dateEvent
                )
            }
            Toast.makeText(applicationContext, "Added to favorite", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to eventId
                )
            }
            Toast.makeText(applicationContext, "Removed from favorite", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs(
                    "EVENT_ID = {id}",
                    "id" to eventId
                )
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}
