package com.rmalan.app.footballleague.fragment


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.rmalan.app.footballleague.R
import com.rmalan.app.footballleague.activity.EventDetailsActivity
import com.rmalan.app.footballleague.adapter.NextMatchAdapter
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.model.Events
import com.rmalan.app.footballleague.preference.MyPreference
import com.rmalan.app.footballleague.presenter.NextMatchPresenter
import com.rmalan.app.footballleague.util.invisible
import com.rmalan.app.footballleague.util.visible
import com.rmalan.app.footballleague.view.NextMatchlView
import org.jetbrains.anko.support.v4.startActivity

class NextMatchFragment : Fragment(), NextMatchlView {

    private var nextMatch: MutableList<Events> = mutableListOf()
    private lateinit var presenter: NextMatchPresenter
    private lateinit var adapter: NextMatchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var rvNextMatch: RecyclerView
    private lateinit var preference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_next_match, container, false)
        setHasOptionsMenu(true)

        progressBar = view.findViewById(R.id.progress_bar_next)

        preference = MyPreference(this.activity!!)
        rvNextMatch = view.findViewById(R.id.rv_next_match)

        adapter = NextMatchAdapter(nextMatch) {
            startActivity<EventDetailsActivity>(EventDetailsActivity.EXTRA_EVENT_ID to it.eventId)
        }
        rvNextMatch.adapter = adapter
        rvNextMatch.setHasFixedSize(true)
        rvNextMatch.layoutManager = LinearLayoutManager(this.activity)

        val request = ApiRepository()
        val gson = Gson()
        presenter = NextMatchPresenter(this, request, gson)
        presenter.getNextMatch(preference.getLeagueId())

        return view
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showNextMatch(data: List<Events>) {
        nextMatch.clear()
        nextMatch.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu);

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                val request = ApiRepository()
                val gson = Gson()
                presenter = NextMatchPresenter(this@NextMatchFragment, request, gson)
                presenter.getSearchEvents(query)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

}
