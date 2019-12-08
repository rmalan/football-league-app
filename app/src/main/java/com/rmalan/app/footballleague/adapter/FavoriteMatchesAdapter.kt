package com.rmalan.app.footballleague.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.model.Favorite
import java.text.ParseException
import java.text.SimpleDateFormat

class FavoriteMatchesAdapter(private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }


}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dateEvent: TextView = view.findViewById(R.id.date_event)
    private val homeTeam: TextView = view.findViewById(R.id.home_team)
    private val homeScore: TextView = view.findViewById(R.id.home_score)
    private val awayTeam: TextView = view.findViewById(R.id.away_team)
    private val awayScore: TextView = view.findViewById(R.id.away_score)

    fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {
        val dateMatch: String? = favorite.dateEvent
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = simpleDateFormat.parse(dateMatch)
            val newFormat = SimpleDateFormat("EE, MM/dd")
            val dateFix = newFormat.format(date)
            dateEvent.text = dateFix
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        homeTeam.text = favorite.homeTeam
        homeScore.text = favorite.homeScore
        awayTeam.text = favorite.awayTeam
        awayScore.text = favorite.awayScore

        itemView.setOnClickListener {
            listener(favorite)
        }
    }
}
