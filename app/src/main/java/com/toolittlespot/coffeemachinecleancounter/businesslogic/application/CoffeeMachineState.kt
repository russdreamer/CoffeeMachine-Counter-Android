package com.toolittlespot.coffeemachinecleancounter.businesslogic.application

import android.app.Activity
import com.toolittlespot.coffeemachinecleancounter.businesslogic.ApplicationState

class CoffeeMachineState(private var maxUseAmount: Int) {
    var useAmount: Int = 0
        private set

    val isClean: Boolean
        get() = useAmount < maxUseAmount

    fun use(activity: Activity) {
        useAmount++
        ApplicationState.saveAppState(activity)
    }

    fun clean(activity: Activity): Boolean {
        useAmount = 0
        ApplicationState.saveAppState(activity)
        return true
    }

    fun setMaxUseAmount(amount: Int, activity: Activity){
        this.maxUseAmount = amount
        ApplicationState.saveAppState(activity)
    }

    fun getMaxUseAmount(): Int{
        return this.maxUseAmount
    }
}
