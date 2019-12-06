package com.rmalan.app.footballleague.model

import com.google.gson.annotations.SerializedName

data class LeagueDetail(
    @SerializedName("strLeague")
    var leagueName: String? = null,

    @SerializedName("intFormedYear")
    var leagueFormed: String? = null,

    @SerializedName("strWebsite")
    var leagueWeb: String? = null,

    @SerializedName("strDescriptionEN")
    var leagueDescription: String? = null,

    @SerializedName("strBadge")
    var leagueBadge: String? = null,

    @SerializedName("strFanart3")
    var leagueFanart: String? = null
)