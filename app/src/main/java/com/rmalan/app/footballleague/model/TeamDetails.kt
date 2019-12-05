package com.rmalan.app.footballleague.model

import com.google.gson.annotations.SerializedName

data class TeamDetails(
    @SerializedName("idHomeTeam")
    var teamId: String? = null,

    @SerializedName("strTeamBadge")
    var teamBadge: String? = null
)