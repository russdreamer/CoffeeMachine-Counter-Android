package com.toolittlespot.coffeemachinecleancounter.businesslogic.application

import java.util.*

class User(var name: String, val avatarPath: String) {

    private var id: Long = 0

    init {
        this.id = Date().time
    }

    fun getId(): Long{
        return this.id
    }

    override fun toString(): String {
        return name
    }
}