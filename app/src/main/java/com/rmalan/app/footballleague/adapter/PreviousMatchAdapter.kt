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

class PreviousMatchAdapter(
    private val prevMatch: List<Events>,
    private val listener: (Events) -> Unit
) : RecyclerView.Adapter<PrevMatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevMatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return PrevMatchViewHolder(view)
    }

    override fun getItemCount(): Int = prevMatch.size

    override fun onBindViewHolder(holder: PrevMatchViewHolder, position: Int) {
        holder.bindItem(prevMatch[position], listener)
    }

}

class PrevMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dateEvent: TextView = view.findViewById(R.id.date_event)
    private val homeTeam: TextView = view.findViewById(R.id.home_team)
    private val homeScore: TextView = view.findViewById(R.id.home_score)
    private val awayTeam: TextView = view.findViewById(R.id.away_team)
    private val awayScore: TextView = view.findViewById(R.id.away_score)

    fun bindItem(prevMatch: Events, listener: (Events) -> Unit) {
        val dateMatch: String? = prevMatch.dateEvent
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = simpleDateFormat.parse(dateMatch)
            val newFormat = SimpleDateFormat("EE, MM/dd")
            val dateFix = newFormat.format(date)
            dateEvent.text = dateFix
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        homeTeam.text = prevMatch.homeTeam
        homeScore.text = prevMatch.homeScore
        awayTeam.text = prevMatch.awayTeam
        awayScore.text = prevMatch.awayScore

        itemView.setOnClickListener {
            listener(prevMatch)
        }
    }
}