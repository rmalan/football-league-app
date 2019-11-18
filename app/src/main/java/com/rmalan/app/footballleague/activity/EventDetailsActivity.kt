package com.rmalan.app.footballleague.activity

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.model.EventDetails
import com.rmalan.app.footballleague.presenter.EventDetailsPresenter
import com.rmalan.app.footballleague.util.invisible
import com.rmalan.app.footballleague.util.visible
import com.rmalan.app.footballleague.view.EventDetailsView
import kotlinx.android.synthetic.main.activity_event_detail.*
import java.text.ParseException
import java.text.SimpleDateFormat

class EventDetailsActivity : AppCompatActivity(), EventDetailsView {

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }

    private lateinit var presenter: EventDetailsPresenter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val eventId = intent.getStringExtra(EXTRA_EVENT_ID)
        Log.d("tag", "id liga = ${eventId}")

        progressBar = findViewById(R.id.progress_bar_event)

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventDetailsPresenter(this, request, gson)
        presenter.getEventDetails(eventId)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEVentDetails(eventDetails: List<EventDetails>) {
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

}
