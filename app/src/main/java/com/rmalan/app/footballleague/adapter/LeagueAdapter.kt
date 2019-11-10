package com.rmalan.app.footballleague.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.rmalan.app.footballleague.entity.LeagueItems
import com.rmalan.app.footballleague.ui.LeagueItemsUI
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext

class LeagueAdapter(
    private val items: List<LeagueItems>,
    private val listener: (LeagueItems) -> Unit
) : RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LeagueItemsUI().createView(AnkoContext.Companion.create(parent.context, parent))
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        val badge = itemView.findViewById<ImageView>(LeagueItemsUI.badgeId)

        fun bindItem(items: LeagueItems, listener: (LeagueItems) -> Unit) {
            items.badge?.let { Picasso.get().load(it).fit().into(badge) }
            itemView.setOnClickListener {
                listener(items)
            }
        }
    }
}