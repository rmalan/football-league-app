package com.rmalan.app.footballleague.view

import com.rmalan.app.footballleague.model.Events

interface PreviousMatchlView {
    fun showPrevMatch(prevMatch: List<Events>)
}