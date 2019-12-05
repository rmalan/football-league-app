package com.rmalan.app.footballleague.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.model.Events
import java.text.ParseException
import java.text.SimpleDateFormat

class MatchAdapter(
    private val match: List<Events>,
    private val listener: (Events) -> Unit
) : RecyclerView.Adapter<MatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun getItemCount(): Int = match.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(match[position], listener)
    }

}

class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dateEvent: TextView = view.findViewById(R.id.date_event)
    private val homeTeam: TextView = view.findViewById(R.id.home_team)
    private val homeScore: TextView = view.findViewById(R.id.home_score)
    private val awayTeam: TextView = view.findViewById(R.id.away_team)
    private val awayScore: TextView = view.findViewById(R.id.away_score)

    fun bindItem(match: Events, listener: (Events) -> Unit) {
        val dateMatch: String? = match.dateEvent
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = simpleDateFormat.parse(dateMatch)
            val newFormat = SimpleDateFormat("EE, MM/dd")
            val dateFix = newFormat.format(date)
            dateEvent.text = dateFix
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        homeTeam.text = match.homeTeam
        homeScore.text = match.homeScore
        awayTeam.text = match.awayTeam
        awayScore.text = match.awayScore

        itemView.setOnClickListener {
            listener(match)
        }
    }
}