package com.toolittlespot.coffeemachinecleancounter.uilogic.views

import java.util.*

class User(var name: String, var avatarPath: String) {

    private var userId: Long = 0

    init {
        this.userId = Date().time
    }

    fun getUserId(): Long{
       return userId
    }
}