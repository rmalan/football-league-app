package com.rmalan.app.footballleague.view

import com.rmalan.app.footballleague.model.TeamDetails

interface TeamDetailsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetails(data: List<TeamDetails>)
}