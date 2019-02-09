package com.toolittlespot.coffeemachinecleancounter.businesslogic

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity

class ApplicationState {
    companion object {

        fun saveAppState(activity: Activity) {
            val prefs = activity.getSharedPreferences("saved_state", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            val json = Gson().toJson(MainActivity.application)
            editor.putString("application", json)
            editor.apply()
        }

        fun loadAppState(activity: Activity): Application? {
            val prefs = activity.getSharedPreferences("saved_state", Context.MODE_PRIVATE)
            val json = prefs.getString("application", null)
            val type = object : TypeToken<Application>() {}.type
            return Gson().fromJson(json, type)
        }

        fun removeAppState(activity: Activity) {
            activity.getSharedPreferences("saved_state", Context.MODE_PRIVATE).edit().clear().apply()
            activity.finish()
            activity.startActivity(activity.intent)
        }
    }
}