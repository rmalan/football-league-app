package com.rmalan.app.footballleague.presenter

import android.util.Log
import com.google.gson.Gson
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.api.TheSportDBApi
import com.rmalan.app.footballleague.model.StandingsResponse
import com.rmalan.app.footballleague.util.CoroutineContextProvider
import com.rmalan.app.footballleague.view.StandingsView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StandingsPresenter(
    private val view: StandingsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getStandingList(leagueId: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getStandings(leagueId)).await(),
                StandingsResponse::class.java
            )

            if (data.table != null) {
                view.hideLoading()
                view.showStandings(data.table)
                Log.d("tag", "responsennya ${data.table}")
            } else {
                view.hideLoading()
                Log.d("tag", "responsennya ${data.table}")
            }

        }
    }

}