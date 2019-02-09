package com.toolittlespot.coffeemachinecleancounter.businesslogic.application

import java.util.ArrayList

class HistoryImpl {
    private var log: MutableList<Action>? = null

    init {
        log = ArrayList()
    }

    fun addAction(action: Action): Boolean {
        return log!!.add(action)
    }

    fun read(): List<Action> {
        return log!!
    }

    fun clear() {
        log!!.clear()
    }
}
