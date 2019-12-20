package com.rmalan.app.footballleague.presenter

import com.google.gson.Gson
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.api.TheSportDBApi
import com.rmalan.app.footballleague.model.TeamDetailsResponse
import com.rmalan.app.footballleague.util.CoroutineContextProvider
import com.rmalan.app.footballleague.view.TeamDetailsView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamDetailsPresenter(private val view: TeamDetailsView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetails(teamId)).await(),
                TeamDetailsResponse::class.java)

            view.showTeamDetails(data.teams)
            view.hideLoading()
        }
    }
}