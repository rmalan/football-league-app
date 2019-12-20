package com.rmalan.app.footballleague.model

import com.google.gson.annotations.SerializedName

data class Standings(
	@SerializedName("name")
	var teamName: String? = null,

	@SerializedName("teamid")
	var teamId: String? = null,

	@SerializedName("played")
	val teamPlayed: String? = null,

	@SerializedName("goalsfor")
	var goalsfor: String? = null,

	@SerializedName("goalsagainst")
	var goalsAgainst: String? = null,

	@SerializedName("goalsdifference")
	var teamGoalsDifference: String? = null,

	@SerializedName("win")
	var teamWin: String? = null,

	@SerializedName("draw")
	var teamDraw: String? = null,

	@SerializedName("loss")
	var teamLoss: String? = null,

	@SerializedName("total")
	var total: String? = null
)