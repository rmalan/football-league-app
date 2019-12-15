package com.rmalan.app.footballleague.presenter

import android.util.Log
import com.google.gson.Gson
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.api.TheSportDBApi
import com.rmalan.app.footballleague.model.EventsResponse
import com.rmalan.app.footballleague.util.CoroutineContextProvider
import com.rmalan.app.footballleague.view.NextMatchlView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextMatchPresenter(
    private val view: NextMatchlView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getNextMatch(leagueId: String?) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getNextEvents(leagueId)).await(),
                EventsResponse::class.java
            )

            if (data.events != null) {
                view.showNextMatch(data.events)
                Log.d("tag", "responsennya ${data.events}")
            } else {
                Log.d("tag", "responsennya ${data.events}")
            }
        }
    }
}