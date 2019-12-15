package com.rmalan.app.footballleague.presenter

import android.util.Log
import com.google.gson.Gson
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.api.TheSportDBApi
import com.rmalan.app.footballleague.model.LeagueDetailResponse
import com.rmalan.app.footballleague.util.CoroutineContextProvider
import com.rmalan.app.footballleague.view.LeagueDetailView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueDetailPresenter(
    private val view: LeagueDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getLeagueDetail(leagueId: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getLeagueDetails(leagueId)).await(),
                LeagueDetailResponse::class.java
            )

            view.hideLoading()
            view.showLeagueDetail(data.leagues)
            Log.d("tag", "responsennya ${data.leagues}")
        }
    }
}