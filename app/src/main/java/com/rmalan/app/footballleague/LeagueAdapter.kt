package com.rmalan.app.footballleague

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class LeagueAdapter(private val context: Context, private val items: List<LeagueItems>, private val listener: (LeagueItems) ->  Unit)
    : RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.league_items, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val badge = view.findViewById<ImageView>(R.id.badge)

        fun bindItem(items: LeagueItems, listener: (LeagueItems) -> Unit) {
            items.badge?.let { Picasso.get().load(it).fit().into(badge) }
            itemView.setOnClickListener {
                listener(items)
            }
        }
    }
}