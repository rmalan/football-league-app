package com.rmalan.app.footballleague.view

import com.rmalan.app.footballleague.model.Standings

interface StandingsView {
    fun showLoading()
    fun hideLoading()
    fun showStandings(data: List<Standings>)
}