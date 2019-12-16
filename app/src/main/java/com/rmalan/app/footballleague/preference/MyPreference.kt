package com.rmalan.app.footballleague.preference

import android.content.Context

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MyPreference(context: Context) {
    companion object {
        const val PREFERENCE_NAME = "MyPreference"
        const val LEAGUE_ID = "league_id"
    }

    val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getLeagueId(): String? {
        return preferences.getString(LEAGUE_ID, "")
    }

    fun setLeagueId(leagueId: String?) {
        val editor = preferences.edit()
        editor.putString(LEAGUE_ID, leagueId)
        editor.apply()
    }
}