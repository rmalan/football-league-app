package com.rmalan.app.footballleague.presenter

import android.util.Log
import com.google.gson.Gson
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.api.TheSportDBApi
import com.rmalan.app.footballleague.model.EventsResponse
import com.rmalan.app.footballleague.model.LeagueDetailResponse
import com.rmalan.app.footballleague.model.SearchResponse
import com.rmalan.app.footballleague.view.LeagueDetailView
import com.rmalan.app.footballleague.view.PreviousMatchlView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PreviousMatchPresenter (
    private val view: PreviousMatchlView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getPrevMatch(leagueId: String?) {
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getPrevEvents(leagueId)),
                EventsResponse::class.java
            )

            uiThread {
                view.showPrevMatch(data.events)
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
                view.showPrevMatch(data.event)
                Log.d("tag", "responsennya ${data.event}")
            }
        }
    }
}