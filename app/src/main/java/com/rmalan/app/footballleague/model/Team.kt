package com.dicoding.picodiploma.myfootballclub

import com.google.gson.annotations.SerializedName

data class Team (
    @SerializedName("idTeam")
    var teamId: String? = null,

    @SerializedName("strSport")
    var sport: String? = null,

    @SerializedName("strTeam")
    var teamName: String? = null,

    @SerializedName("strTeamBadge")
    var teamBadge: String? = null
)