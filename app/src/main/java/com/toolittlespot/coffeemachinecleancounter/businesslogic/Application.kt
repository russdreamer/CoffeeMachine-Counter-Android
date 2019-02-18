package com.toolittlespot.coffeemachinecleancounter.businesslogic

import android.app.Activity
import android.content.Context
import com.toolittlespot.coffeemachinecleancounter.businesslogic.application.*
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.LangMap
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.History
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.Statistics
import java.io.File
import java.util.*

class Application {
    private var usersPanel: UsersPanel = UsersPanel()
    private var coffeeMachineState: CoffeeMachineState = CoffeeMachineState(0)
    private var history: HistoryImpl = HistoryImpl()
    private var dict: LangMap = LangMap()

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
        coffeeMachineState.use()
        history.addAction(Action(user, ActionType.USE, Date()))
        History.adapter.notifyDataSetChanged()
        updateStats()
        ApplicationState.saveAppState(activity)
    }

    fun cleanCoffeeMachine(activity: Activity){
        coffeeMachineState.clean()
        if (history.read().isNotEmpty()){
            val lastUser = history.read().last().user
            history.addAction(Action(lastUser, ActionType.CLEAN, Date()))
            History.adapter.notifyDataSetChanged()
            updateStats()
        }
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
}