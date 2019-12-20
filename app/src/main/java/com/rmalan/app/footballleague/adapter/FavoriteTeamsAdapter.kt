package com.rmalan.app.footballleague.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.model.FavoriteTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class FavoriteTeamsAdapter(private val favoriteTeams: List<FavoriteTeam>, private val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamsViewHolder {
        return FavoriteTeamsViewHolder(FavoriteTeamsUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavoriteTeamsViewHolder, position: Int) {
        holder.bindItem(favoriteTeams[position], listener)
    }

    override fun getItemCount(): Int = favoriteTeams.size

}

class FavoriteTeamsUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout{
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.team_badge
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.team_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }

            }
        }
    }
}

class FavoriteTeamsViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val teamBadge: ImageView = view.findViewById(R.id.team_badge)
    private val teamName: TextView = view.findViewById(R.id.team_name)

    fun bindItem(favoriteTeam: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
        Picasso.get().load(favoriteTeam.teamBadge).fit().into(teamBadge)
        teamName.text = favoriteTeam.teamName
        itemView.setOnClickListener { listener(favoriteTeam) }
    }
}