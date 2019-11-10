package com.rmalan.app.footballleague.ui

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.rmalan.app.footballleague.R
import org.jetbrains.anko.*


class LeagueItemsUI : AnkoComponent<ViewGroup> {

    companion object {
        val badgeId = 1
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            orientation = LinearLayout.HORIZONTAL
            imageView {
                id = badgeId
                contentDescription = resources.getString(R.string.badge)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }.lparams(width = matchParent, height = dip(225)) {
                margin = dip(16)
                marginStart = dip(8)
                topMargin = dip(8)
                marginEnd = dip(8)
                bottomMargin = dip(8)
            }
        }
    }
}
