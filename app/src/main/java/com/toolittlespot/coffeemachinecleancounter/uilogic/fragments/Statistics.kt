package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.adapter.StatAdapter
import com.toolittlespot.coffeemachinecleancounter.businesslogic.application.StatListGenerator
import com.toolittlespot.coffeemachinecleancounter.businesslogic.application.User
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity

class Statistics : Fragment() {
    private lateinit var fragmentView: View

    companion object {
        lateinit var useAdapter: StatAdapter
        lateinit var cleanAdapter: StatAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView = inflater.inflate(R.layout.fragment_statistics, container, false)
        val statGen = StatListGenerator()
        configTextViews()
        configUseList(statGen.stats.useList)
        configCleanList(statGen.stats.cleanList)

        return fragmentView
    }

    private fun configTextViews() {
        fragmentView.findViewById<TextView>(R.id.coffee_maniac_rank).text = MainActivity.app.getDict(Dict.TOP_COFFEE_MANIACS)
        fragmentView.findViewById<TextView>(R.id.janitor_rank).text = MainActivity.app.getDict(Dict.TOP_JANITORS)
    }

    private fun configCleanList(statList: List<Statistics.StatisticPOJO>) {
        val list = fragmentView.findViewById<ListView>(R.id.janitor_rank_list)

        cleanAdapter = StatAdapter(
            activity,
            statList,
            R.layout.stat_item,
            StatAdapter.AdapterType.CLEAN
        )

        list.adapter = cleanAdapter
    }

    private fun configUseList( statList: List<Statistics.StatisticPOJO>) {
        val list = fragmentView.findViewById<ListView>(R.id.coffee_maniac_rank_list)

        useAdapter = StatAdapter(
            activity,
            statList,
            R.layout.stat_item,
            StatAdapter.AdapterType.USE
        )

        list.adapter = useAdapter
    }


    class StatisticPOJO (val user: User) {
        var useTimes: Long = 0

        var cleanTimes: Long = 0

        fun incUseTimes(){
            useTimes ++
        }

        fun incCleanTimes(){
            cleanTimes ++
        }
    }


}
