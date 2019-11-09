package com.rmalan.app.footballleague

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var items: MutableList<LeagueItems> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = findViewById<RecyclerView>(R.id.league_list)
        initData()

        list.layoutManager = GridLayoutManager(this, 2)
        list.adapter = LeagueAdapter(this, items) {
            val toast = Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun initData() {
        val name = resources.getStringArray(R.array.league_name)
        val description = resources.getStringArray(R.array.description)
        val badge = resources.obtainTypedArray(R.array.league_badge)
        items.clear()
        for (i in name.indices) {
            items.add(LeagueItems(name[i],
                description[i],
                badge.getResourceId(i, 0)))
        }

        badge.recycle()
    }
}
