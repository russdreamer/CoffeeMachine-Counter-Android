package com.toolittlespot.coffeemachinecleancounter.businesslogic.application

class CoffeeMachineState(var maxUseAmount: Int) {
    var useAmount: Int = 0
        private set

    val isClean: Boolean
        get() = useAmount < maxUseAmount

    fun use() {
        useAmount++
    }

    fun clean() {
        useAmount = 0
    }
}
