package com.rmalan.app.footballleague.presenter

import android.util.Log
import com.google.gson.Gson
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.api.TheSportDBApi
import com.rmalan.app.footballleague.model.EventDetailsResponse
import com.rmalan.app.footballleague.model.LeagueDetailResponse
import com.rmalan.app.footballleague.view.EventDetailsView
import com.rmalan.app.footballleague.view.LeagueDetailView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventDetailsPresenter(
    private val view: EventDetailsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getEventDetails(eventId: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEventDetails(eventId)),
                EventDetailsResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showEVentDetails(data.events)
                Log.d("tag", "responsennya ${data.events}")
            }
        }
    }
}