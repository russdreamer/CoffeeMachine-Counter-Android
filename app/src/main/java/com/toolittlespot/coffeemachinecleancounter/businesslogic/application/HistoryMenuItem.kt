package com.toolittlespot.coffeemachinecleancounter.businesslogic.application

class HistoryMenuItem(private val history: History<Action>) {

    val content: String
        get() {
            val content = StringBuilder()
            content.append("История:")
            getActions(content)
            return content.toString()
        }

    private fun getActions(content: StringBuilder) {
        val log = history.read()
        for (action in log) {
            content.append("\n")
            content.append(action.user)
            content.append(action.type)
            content.append(action.date)
        }
    }
}