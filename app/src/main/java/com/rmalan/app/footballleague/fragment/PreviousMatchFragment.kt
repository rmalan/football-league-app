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
import com.rmalan.app.footballleague.adapter.PreviousMatchAdapter
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.model.Events
import com.rmalan.app.footballleague.preference.MyPreference
import com.rmalan.app.footballleague.presenter.PreviousMatchPresenter
import com.rmalan.app.footballleague.util.invisible
import com.rmalan.app.footballleague.util.visible
import com.rmalan.app.footballleague.view.PreviousMatchlView
import org.jetbrains.anko.support.v4.startActivity

class PreviousMatchFragment : Fragment(), PreviousMatchlView {

    private var prevMatch: MutableList<Events> = mutableListOf()
    private lateinit var presenter: PreviousMatchPresenter
    private lateinit var adapter: PreviousMatchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var rvPrevMatch: RecyclerView
    private lateinit var preference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_previous_match, container, false)

        progressBar = view.findViewById(R.id.progress_bar_prev)

        preference = MyPreference(this.activity!!)
        rvPrevMatch = view.findViewById(R.id.rv_prev_match)

        adapter = PreviousMatchAdapter(prevMatch) {
            startActivity<EventDetailsActivity>(EventDetailsActivity.EXTRA_EVENT_ID to it.eventId)
        }
        rvPrevMatch.adapter = adapter
        rvPrevMatch.setHasFixedSize(true)
        rvPrevMatch.layoutManager = LinearLayoutManager(this.activity)

        val request = ApiRepository()
        val gson = Gson()
        presenter = PreviousMatchPresenter(this, request, gson)
        presenter.getPrevMatch(preference.getLeagueId())

        return view
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPrevMatch(data: List<Events>) {
        prevMatch.clear()
        prevMatch.addAll(data)
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
                presenter = PreviousMatchPresenter(this@PreviousMatchFragment, request, gson)
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
