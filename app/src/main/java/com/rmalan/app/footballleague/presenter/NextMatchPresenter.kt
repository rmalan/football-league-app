package com.rmalan.app.footballleague.presenter

import android.util.Log
import com.google.gson.Gson
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.api.TheSportDBApi
import com.rmalan.app.footballleague.model.EventsResponse
import com.rmalan.app.footballleague.model.SearchResponse
import com.rmalan.app.footballleague.view.NextMatchlView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NextMatchPresenter(
    private val view: NextMatchlView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getNextMatch(leagueId: String?) {
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getNextEvents(leagueId)),
                EventsResponse::class.java
            )

            uiThread {
                view.showNextMatch(data.events)
                Log.d("tag", "responsennya ${data.events}")
            }
        }
    }

    fun getSearchEvents(query: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getSearchEvents(query)),
                SearchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showNextMatch(data.event)
                Log.d("tag", "responsennya ${data.event}")
            }
        }
    }
}