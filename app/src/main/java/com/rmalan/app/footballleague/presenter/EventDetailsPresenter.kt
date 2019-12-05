package com.rmalan.app.footballleague.presenter

import android.util.Log
import com.google.gson.Gson
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.api.TheSportDBApi
import com.rmalan.app.footballleague.model.EventDetailsResponse
import com.rmalan.app.footballleague.model.TeamDetailsResponse
import com.rmalan.app.footballleague.view.EventDetailsView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventDetailsPresenter(
    private val view: EventDetailsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getEventDetails(eventId: String?, homeTeamId: String?, awayTeamId: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEventDetails(eventId)),
                EventDetailsResponse::class.java
            )

            val home = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamDetails(homeTeamId)),
                TeamDetailsResponse::class.java
            )

            val away = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamDetails(awayTeamId)),
                TeamDetailsResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showEVentDetails(data.events, home.teams, away.teams)
                Log.d(
                    "tag",
                    "responsennya ${data.events}/n home ${home.teams}/n home ${away.teams}"
                )
            }
        }
    }
}