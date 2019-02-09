package com.toolittlespot.coffeemachinecleancounter.businesslogic

import com.toolittlespot.coffeemachinecleancounter.businesslogic.application.*

class Application {
    var usersPanel: UsersPanel = UsersPanel()
    var coffeeMachineState: CoffeeMachineState = CoffeeMachineState(0)
    var history: HistoryImpl = HistoryImpl()
}