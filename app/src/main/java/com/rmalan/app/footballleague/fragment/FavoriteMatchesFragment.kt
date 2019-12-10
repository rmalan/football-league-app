package com.rmalan.app.footballleague.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.activity.EventDetailsActivity
import com.rmalan.app.footballleague.adapter.FavoriteMatchesAdapter
import com.rmalan.app.footballleague.db.database
import com.rmalan.app.footballleague.model.Favorite
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

class FavoriteMatchesFragment : Fragment() {

    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteMatchesAdapter
    private lateinit var rvFavoriteMathces: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_matches, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvFavoriteMathces = view!!.findViewById(R.id.rv_favorite_matches)

        adapter = FavoriteMatchesAdapter(favorites) {
            startActivity<EventDetailsActivity>(
                EventDetailsActivity.EXTRA_EVENT_ID to it.eventId,
                EventDetailsActivity.EXTRA_HOME_TEAM_ID to it.homeTeamId,
                EventDetailsActivity.EXTRA_AWAY_TEAM_ID to it.awayTeamId
            )
        }

        rvFavoriteMathces.adapter = adapter
        rvFavoriteMathces.setHasFixedSize(true)
        rvFavoriteMathces.layoutManager = LinearLayoutManager(this.activity)
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

}
