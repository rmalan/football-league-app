package com.rmalan.app.footballleague.presenter

import android.util.Log
import com.google.gson.Gson
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.api.TheSportDBApi
import com.rmalan.app.footballleague.model.EventDetailsResponse
import com.rmalan.app.footballleague.model.TeamDetailsResponse
import com.rmalan.app.footballleague.util.CoroutineContextProvider
import com.rmalan.app.footballleague.view.EventDetailsView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventDetailsPresenter(
    private val view: EventDetailsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getEventDetails(eventId: String?, homeTeamId: String?, awayTeamId: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEventDetails(eventId)).await(),
                EventDetailsResponse::class.java
            )

            val home = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamDetails(homeTeamId)).await(),
                TeamDetailsResponse::class.java
            )

            val away = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamDetails(awayTeamId)).await(),
                TeamDetailsResponse::class.java
            )

            view.hideLoading()
            view.showEVentDetails(data.events, home.teams, away.teams)
            Log.d(
                "tag",
                "responsennya ${data.events}/n home ${home.teams}/n home ${away.teams}"
            )
        }
    }
}