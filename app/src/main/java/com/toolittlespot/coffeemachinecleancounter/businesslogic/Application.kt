package com.toolittlespot.coffeemachinecleancounter.businesslogic

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.support.v4.app.FragmentActivity
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.application.*
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.LangMap
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.AchievementChooser
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.History
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.SecretMessage
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.Statistics
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class Application {
    private var usersPanel: UsersPanel = UsersPanel()
    private var coffeeMachineState: CoffeeMachineState = CoffeeMachineState(0)
    private var history: HistoryImpl = HistoryImpl()
    private var dict: LangMap = LangMap()
    private var achievements:AchievementHolder = AchievementHolder()

    fun getAchievements(): ArrayList<Achievement> {
        return achievements.achievementList
    }

    fun reachAchievement(activity: Activity) {
        playAchievementSound(activity)
        AchievementChooser.selectedUser = null
        AchievementChooser.selectedAchievement = null
        achievements.generate()
    }

    fun setNewAchievement(user: User?, item: Achievement, activity: Activity){
        achievements.selectAchievement(user, item)
        ApplicationState.saveAppState(activity)
    }

    fun getCurrentAchievement(): Achievement?{
        return achievements.currentAchievement
    }

    fun getDict(word: Dict): String {
        return dict.getDict(word)
    }

    fun changeLanguage(language: String){
        dict.changeLanguage(language)
    }

    fun getUsers(): List<User> {
        return usersPanel.getUsers()
    }

    fun getUser(userId: Long): User? {
        return usersPanel.getUser(userId)
    }

    fun addUser(name: String, activity: Activity): User?{
        val user = createUser(name, activity)
        val res = usersPanel.addUser(user)
        updateStats()
        ApplicationState.saveAppState(activity)
        return res
    }

    fun removeUser(user: User, activity: Activity): User? {
        val res = usersPanel.removeUser(user)
        File(user.avatarPath).delete()
        updateStats()
        ApplicationState.saveAppState(activity)
        return res
    }

    fun updateUser(user: User, name: String, activity: Activity){
        usersPanel.updateUser(user,name)
        AppUtils().saveTempImageAsUserPic(File(user.avatarPath).name, activity)
        updateStats()
        ApplicationState.saveAppState(activity)
    }

    private fun createUser(name: String, context: Context): User {
        val imageName = Date().time.toString()
        val imageFile = AppUtils().saveTempImageAsUserPic(imageName, context)
        return User(name, imageFile.absolutePath)
    }

    fun useCoffeeMachine(user: User, activity: Activity){
        playUseMachineSound(user, activity)
        showSecretMessage(user, activity)
        if ( isCleanHandsAchievement(user, activity)) {
            achievements.maxCleanHandsAchieveTimes--
        }
        else coffeeMachineState.use()

        history.addAction(Action(user, ActionType.USE, Date()))
        History.adapter.notifyDataSetChanged()
        updateStats()
        ApplicationState.saveAppState(activity)
    }

    fun cleanCoffeeMachine(user: User, activity: Activity){
        playCleanMachineSound(activity)
        coffeeMachineState.clean()
        history.addAction(Action(user, ActionType.CLEAN, Date()))
        History.adapter.notifyDataSetChanged()
        updateStats()
        ApplicationState.saveAppState(activity)
    }

    fun setMaxUseAmountMachine(amount: Int, activity: Activity){
        coffeeMachineState.maxUseAmount = amount
        ApplicationState.saveAppState(activity)
    }

    fun getMaxUseAmountMachine(): Int {
        return coffeeMachineState.maxUseAmount
    }

    fun isMachineClean(): Boolean {
        return coffeeMachineState.isClean
    }

    fun clearHistory(activity: Activity){
        history.clear()
        History.adapter.notifyDataSetChanged()
        updateStats()
        ApplicationState.saveAppState(activity)
    }

    fun getHistory(): MutableList<Action> {
        return history.read()
    }

    private fun updateStats() {
        val statGen = StatListGenerator()
        Statistics.useAdapter.mData = statGen.stats.useList
        Statistics.cleanAdapter.mData = statGen.stats.cleanList
        Statistics.useAdapter.notifyDataSetChanged()
        Statistics.cleanAdapter.notifyDataSetChanged()
    }

    private fun playCleanMachineSound(activity: Activity) {
        val media = MediaPlayer.create(activity, R.raw.clean)
        media.setOnCompletionListener {
            media.release()
        }
        media.start()
    }

    private fun playAchievementSound(activity: Activity) {
        val media = MediaPlayer.create(activity, R.raw.achievement)
        media.setOnCompletionListener {
            media.release()
        }
        media.start()
    }

    private fun playSecretMessageSound(activity: Activity) {
        val media = MediaPlayer.create(activity, R.raw.secret_message)
        media.setOnCompletionListener {
            media.release()
        }
        media.start()
    }

    private fun playUseMachineSound(user: User, activity: Activity) {
        val media:MediaPlayer
        media = if (
            achievements.currentAchievement?.type == Achievement.Type.SOUNDS_FUNNY
            && achievements.chosenUser?.getId() == user.getId()
        ) {
            val num = Random.nextInt(1,6)
            val resID = activity.resources.getIdentifier("fart$num", "raw", activity.packageName)
            MediaPlayer.create(activity, resID)

        } else MediaPlayer.create(activity, R.raw.use)

        media.setOnCompletionListener {
            it.release()
        }
        media.start()
    }

    private fun showSecretMessage(user: User, activity: Activity) {
        if (achievements.isAchievementActive
            && achievements.currentAchievement?.type == Achievement.Type.SECRET_MESSAGE
            && user.getId() == achievements.chosenUser?.getId()){

            SecretMessage().show((activity as FragmentActivity).supportFragmentManager, "")
            playSecretMessageSound(activity)
            achievements.isAchievementActive = false
        }
    }

    private fun isCleanHandsAchievement(user: User, activity: Activity): Boolean{
        return achievements.currentAchievement?.type == Achievement.Type.CLEAN_HANDS
            && achievements.chosenUser?.getId() == user.getId()
            && isLastTimeToUse()
            && achievements.maxCleanHandsAchieveTimes > 0
    }

    private fun isLastTimeToUse(): Boolean {
        return (coffeeMachineState.maxUseAmount - coffeeMachineState.useAmount) == 1
    }
}