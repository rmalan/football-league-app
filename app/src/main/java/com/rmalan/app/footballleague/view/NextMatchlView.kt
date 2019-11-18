package com.rmalan.app.footballleague.view

import com.rmalan.app.footballleague.model.Events

interface NextMatchlView {
    fun showLoading()
    fun hideLoading()
    fun showNextMatch(nextMatch: List<Events>)
}