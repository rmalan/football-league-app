package com.rmalan.app.footballleague.view

import com.rmalan.app.footballleague.model.LeagueDetail

interface LeagueDetailView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueDetail(leagueDetail: List<LeagueDetail>)
}