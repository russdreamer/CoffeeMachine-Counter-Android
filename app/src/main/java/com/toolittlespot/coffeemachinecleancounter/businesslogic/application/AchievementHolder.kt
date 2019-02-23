package com.toolittlespot.coffeemachinecleancounter.businesslogic.application

import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity

class AchievementHolder {
    var achievementList = ArrayList<Achievement>()
    var currentAchievement: Achievement? = null
    var chosenUser: User? = null
    var isAchievementActive = false
    var maxCleanHandsAchieveTimes: Int = 0

    fun generate(){
        achievementList.clear()
        generateList()
        currentAchievement = noneAchieve()
        maxCleanHandsAchieveTimes = getMaxDirtyTimes()
    }

    private fun getMaxDirtyTimes(): Int {
        val max = MainActivity.app.getMaxUseAmountMachine()
        return Math.ceil(max / 15.0).toInt()
    }

    fun selectAchievement(user: User?, item: Achievement){
        chosenUser = user
        currentAchievement = item
        isAchievementActive = true
    }

    private fun generateList() {
        Achievement.Type.values().forEach {
            val item = createAchievement(it)
            if (item != null)
                achievementList.add(item)
        }
    }

    private fun createAchievement(type: Achievement.Type): Achievement?{
        return when (type.name){
            Achievement.Type.CLEAN_HANDS.name -> cleanHandsAchieve()
            Achievement.Type.SOUNDS_FUNNY.name -> soundsFunnyAchieve()
            Achievement.Type.SECRET_MESSAGE.name -> secretMessageAchieve()
            Achievement.Type.NONE.name -> noneAchieve()
            else -> null
        }
    }

    private fun noneAchieve(): Achievement {
        val name = MainActivity.app.getDict(Dict.NONE)
        val description = MainActivity.app.getDict(Dict.NONE_DESCRIPTION)
        return Achievement(name, Achievement.Type.NONE, description)
    }

    private fun secretMessageAchieve(): Achievement {
        val name = MainActivity.app.getDict(Dict.SECRET_MESSAGE)
        val description = MainActivity.app.getDict(Dict.SECRET_MESSAGE_DESCRIPTION)
        return Achievement(name, Achievement.Type.SECRET_MESSAGE, description)
    }

    private fun soundsFunnyAchieve(): Achievement {
        val name = MainActivity.app.getDict(Dict.SOUNDS_FUNNY)
        val description = MainActivity.app.getDict(Dict.SOUNDS_FUNNY_DESCRIPTION)
        return Achievement(name, Achievement.Type.SOUNDS_FUNNY, description)
    }

    private fun cleanHandsAchieve(): Achievement {
        val name = MainActivity.app.getDict(Dict.CLEAN_HANDS)
        val description = MainActivity.app.getDict(Dict.CLEAN_HANDS_DESCRIPTION)
        return Achievement(name, Achievement.Type.CLEAN_HANDS, description)
    }
}