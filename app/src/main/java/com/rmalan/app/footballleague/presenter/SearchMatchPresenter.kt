package com.rmalan.app.footballleague.presenter

import android.util.Log
import com.google.gson.Gson
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.api.TheSportDBApi
import com.rmalan.app.footballleague.model.SearchResponse
import com.rmalan.app.footballleague.view.SearchMatchlView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SearchMatchPresenter(
    private val view: SearchMatchlView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getSearchEvents(query: String?) {
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getSearchEvents(query)),
                SearchResponse::class.java
            )

            uiThread {
                try {
                    view.showSearchMatch(data.event.filter { it.sport == "Soccer" })
                    Log.d("tag", "responsennya ${data.event.filter { it.sport == "Soccer" }}")
                } catch(e: NullPointerException) {
                    Log.d("tag", "null")
                }
            }
        }
    }
}