package com.toolittlespot.coffeemachinecleancounter.businesslogic.application

import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.Statistics

class StatListGenerator {
    private val statMap = HashMap<Long, Statistics.StatisticPOJO>()
    var stats: StatListObject

    init {
        createStatisticMap()
        fillStatisticMap()
        val statList = statMap.values
        val useStat =  statList.sortedByDescending { it.useTimes }
        val cleanStat =  statList.sortedByDescending { it.cleanTimes }
        stats = StatListObject(useStat, cleanStat)
    }

    private fun fillStatisticMap() {
        val logList = MainActivity.app.getHistory()
        logList.forEach{
            val userID = it.user.getId()
            if (it.type == ActionType.USE)
                statMap[userID]?.incUseTimes()

            else statMap[userID]?.incCleanTimes()
        }
    }

    private fun createStatisticMap(){
        val userList = MainActivity.app.getUsers()
        userList.forEach{
            statMap[it.getId()] = Statistics.StatisticPOJO(it)
        }
    }

    class StatListObject(val useList: List<Statistics.StatisticPOJO>, val cleanList: List<Statistics.StatisticPOJO>)

}