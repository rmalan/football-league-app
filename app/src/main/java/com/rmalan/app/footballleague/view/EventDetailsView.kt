package com.rmalan.app.footballleague.view

import com.rmalan.app.footballleague.model.EventDetails
import com.rmalan.app.footballleague.model.TeamDetails

interface EventDetailsView {
    fun showLoading()
    fun hideLoading()
    fun showEVentDetails(
        eventDetails: List<EventDetails>,
        homeTeamDetails: List<TeamDetails>,
        awayTeamDetails: List<TeamDetails>
    )
}