package com.rmalan.app.footballleague.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.model.Standings

class StandingsAdapter(private val standings: List<Standings>) :
    RecyclerView.Adapter<StandingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_standing, parent, false)
        return StandingViewHolder(view)
    }

    override fun getItemCount(): Int = standings.size

    override fun onBindViewHolder(holder: StandingViewHolder, position: Int) {
        holder.bindItem(standings[position])
    }

}

class StandingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val teamName: TextView = view.findViewById(R.id.team_name_standing)
    private val played: TextView = view.findViewById(R.id.played)
    private val teamWin: TextView = view.findViewById(R.id.win)
    private val teamDraw: TextView = view.findViewById(R.id.draw)
    private val teamLoss: TextView = view.findViewById(R.id.loss)
    private val total: TextView = view.findViewById(R.id.total)

    fun bindItem(standings: Standings) {
        teamName.text = standings.teamName
        played.text = standings.teamPlayed
        teamWin.text = standings.teamWin
        teamDraw.text = standings.teamDraw
        teamLoss.text = standings.teamLoss
        total.text = standings.total
    }
}
