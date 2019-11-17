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

class NextMatchAdapter (private val nextMatch: List<Events>)
    : RecyclerView.Adapter<NextMatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextMatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return NextMatchViewHolder(view)
    }

    override fun getItemCount(): Int = nextMatch.size

    override fun onBindViewHolder(holder: NextMatchViewHolder, position: Int) {
        holder.bindItem(nextMatch[position])
    }

}

class NextMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dateEvent: TextView = view.findViewById(R.id.date_event)
    private val homeTeam: TextView = view.findViewById(R.id.home_team)
    private val homeScore: TextView = view.findViewById(R.id.home_score)
    private val awayTeam: TextView = view.findViewById(R.id.away_team)
    private val awayScore: TextView = view.findViewById(R.id.away_score)

    fun bindItem(nextMatch: Events) {
        val dateMatch: String? = nextMatch.dateEvent
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = simpleDateFormat.parse(dateMatch)
            val newFormat = SimpleDateFormat("EE, MM/dd")
            val dateFix = newFormat.format(date)
            dateEvent.text = dateFix
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        homeTeam.text = nextMatch.homeTeam
        homeScore.text = nextMatch.homeScore
        awayTeam.text = nextMatch.awayTeam
        awayScore.text = nextMatch.awayScore
    }
}