package com.rmalan.app.footballleague.presenter

import android.util.Log
import com.google.gson.Gson
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.api.TheSportDBApi
import com.rmalan.app.footballleague.model.LeagueDetailResponse
import com.rmalan.app.footballleague.view.LeagueDetailView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeagueDetailPresenter(
    private val view: LeagueDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getLeagueDetail(leagueId: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getLeagueDetails(leagueId)),
                LeagueDetailResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showLeagueDetail(data.leagues)
                Log.d("tag", "responsennya ${data.leagues}")
            }
        }
    }
}