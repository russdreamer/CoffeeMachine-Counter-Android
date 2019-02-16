package com.toolittlespot.coffeemachinecleancounter.businesslogic.application

import java.util.ArrayList

class HistoryImpl {
    private var log: MutableList<Action> = ArrayList()

    fun addAction(action: Action): Boolean {
        return log.add(action)
    }

    fun read(): MutableList<Action> {
        return log
    }

    fun clear() {
        log.clear()
    }
}
