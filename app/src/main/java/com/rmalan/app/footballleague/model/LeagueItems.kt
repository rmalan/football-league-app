package com.rmalan.app.footballleague.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueItems(
    val id: String?,
    val name: String?,
    val description: String?,
    val badge: Int?
) : Parcelable