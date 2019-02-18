package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.adapter.HistoryAdapter
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
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
        configViews()
        return fragmentView
    }

    private fun configViews() {
        configList()
        configTitles()
    }

    private fun configTitles() {
        fragmentView.findViewById<TextView>(R.id.time_column_txt).text = MainActivity.app.getDict(Dict.TIME)
        fragmentView.findViewById<TextView>(R.id.avatar_column_txt).text = MainActivity.app.getDict(Dict.AVATAR)
        fragmentView.findViewById<TextView>(R.id.name_column_txt).text = MainActivity.app.getDict(Dict.NAME)
        fragmentView.findViewById<TextView>(R.id.action_column_txt).text = MainActivity.app.getDict(Dict.ACTION)
    }

    private fun configList() {
        val list = fragmentView.findViewById<ListView>(R.id.history_list)

        adapter = HistoryAdapter(
            context!!,
            MainActivity.app.getHistory(),
            R.layout.history_item
            )

        list.adapter = adapter
    }
}
