package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.adapter.HistoryAdapter
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity

class History : Fragment() {
    companion object {
        lateinit var adapter: HistoryAdapter
    }
    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_history, container, false)
        MainActivity.application.getHistory()
        configList()
        return fragmentView
    }

    private fun configList() {
        val list = fragmentView.findViewById<ListView>(R.id.history_list)

        adapter = HistoryAdapter(
            context!!,
            MainActivity.application.getHistory(),
            R.layout.history_item
            )

        list.adapter = adapter
    }
}
