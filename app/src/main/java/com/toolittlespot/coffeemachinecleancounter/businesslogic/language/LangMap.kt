package com.toolittlespot.coffeemachinecleancounter.businesslogic.language

import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils


class LangMap {
    var currentDict = HashMap<String, String>()
    private var dictLoaded: Boolean = false

    /**
     * getting language dictionary with  user language
     * @return language dictionary
     */
    fun getDict(): Map<String, String>{
        if (!dictLoaded) {
            determinateUserLanguage()
        }
        return currentDict;
    }

    /**
     * getting a phrase from language dictionary in current language
     * @param word key word from language dictionary to extract
     * @return phrase from language dictionary
     */
    fun getDict(word: Dict): String {

        return getDict()[word.name].toString()
    }

    /**
     * determination user's system language
     */
    private fun determinateUserLanguage() {
        val language = AppUtils().getLocalLanguage()
        changeLanguage(language)
    }

    /**
     * changing current language to chosen one
     * @param language language to change
     */
    fun changeLanguage(language: String) {
        when (language) {
            "en" -> getEnglishDictionary()
            "ru" -> getRussianDictionary()
            else -> getEnglishDictionary()
        }
        dictLoaded = true
    }

    private fun getRussianDictionary() {
        val keys = Dict.values()

        for (key in keys) {
            currentDict[key.name] = key.russian
        }
    }

    /**
     * loading language dictionary to use
     */
    private fun getEnglishDictionary() {
        val keys = Dict.values()

        for (key in keys) {
            currentDict[key.name] = key.english
        }
    }
}