package com.rmalan.app.footballleague.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueItems(val name: String?, val description: String?, val badge: Int?) : Parcelable