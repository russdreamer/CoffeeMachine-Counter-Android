package com.toolittlespot.coffeemachinecleancounter.businesslogic

import com.toolittlespot.coffeemachinecleancounter.uilogic.views.User

class Application {
    var users = hashMapOf<Long, User>()
    var maxUsesAmount: Int = 0
}