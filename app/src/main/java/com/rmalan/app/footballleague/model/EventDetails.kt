package com.rmalan.app.footballleague.model

import com.google.gson.annotations.SerializedName

data class EventDetails(
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("strLeague")
    var leaggueName: String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam: String? = null,

    @SerializedName("strAwayTeam")
    var awayTeam: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: String? = null,

    @SerializedName("intAwayScore")
    var awayScore: String? = null,

    @SerializedName("dateEvent")
    var dateEvent: String? = null,

    @SerializedName("strHomeGoalDetails")
    var homeGoal: String? = null,

    @SerializedName("strAwayGoalDetails")
    var awayGoal: String? = null,

    @SerializedName("intHomeShots")
    var homeshots: String? = null,

    @SerializedName("intAwayShots")
    var awayShots: String? = null,

    @SerializedName("strHomeYellowCards")
    var homeYellow: String? = null,

    @SerializedName("strAwayYellowCards")
    var awayYellow: String? = null,

    @SerializedName("strHomeRedCards")
    var homeRed: String? = null,

    @SerializedName("strAwayRedCards")
    var awayRed: String? = null
)