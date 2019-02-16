package com.toolittlespot.coffeemachinecleancounter.businesslogic.application

import java.util.HashMap
import java.util.stream.Collectors

class StatisticsMenuItem(private val history: HistoryImpl) {

    val content: String
        get() = coffeeMachineUseAmount + coffeeMachineCleanAmount

    private//StringBuilder usingStat = getHistoryContent(history);
    //getMapContent(usingStat, cafeMachineUseAmount);
    val coffeeMachineUseAmount: String
        get() {

            val usingStat = StringBuilder()
            usingStat.append("Рейтинг кофеманов:")
            usingStat.append("\n")

            return usingStat.toString()
        }

    /* private StringBuilder getHistoryContent(History<Action> history) {
        Map<User, Integer> coffeeMachineUseAmount = new HashMap<>();
        Map<User, Integer> coffeeMachineCleanAmount = new HashMap<>();

        coffeeMachineCleanAmount.p

        history.read().stream()
                .filter(action -> ActionType.USE == action.getType())
                .collect(Collectors.toSet())
    }*/

    private//getMapContent(cleanStat, cafeMachineCleanAmount);
    val coffeeMachineCleanAmount: String
        get() {
            val cleanStat = StringBuilder()
            cleanStat.append("Рейтинг гувернанток:")
            cleanStat.append("\n")

            return cleanStat.toString()
        }

    /* private void getMapContent(StringBuilder builder, Map<User, Integer> map) {
        map.forEach((x, y) -> {
            builder.append(x.getName());
            builder.append(" = ");
            builder.append(y);
            builder.append(System.lineSeparator());
        });
    }*/
}
