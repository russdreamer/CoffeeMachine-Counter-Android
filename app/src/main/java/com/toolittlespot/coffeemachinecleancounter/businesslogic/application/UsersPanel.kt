package com.toolittlespot.coffeemachinecleancounter.businesslogic.application

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentActivity
import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils
import com.toolittlespot.coffeemachinecleancounter.businesslogic.ApplicationState
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity
import java.io.File
import java.util.*

class UsersPanel {
    private var users: HashMap<Long, User> = HashMap()

    fun getUsers(): List<User> {
        return ArrayList(users.values)
    }

    fun addUser(name: String, activity: Activity): User? {
        val user = createUser(name, activity)
        val res = users.put(user.getId(), user)
        ApplicationState.saveAppState(activity)
        return res
    }

    fun removeUser(user: User, activity: Activity): User? {
        val res = users.remove(user.getId())
        File(user.avatarPath).delete()
        ApplicationState.saveAppState(activity)
        return res
    }

    fun updateUser(user: User, name: String, activity: Activity){
        user.name = name
        AppUtils().saveTempImageAsUserPic(File(user.avatarPath).name, activity)
        ApplicationState.saveAppState(activity)
    }

    private fun createUser(name: String, context: Context): User {
        val imageName = Date().time.toString()
        val imageFile = AppUtils().saveTempImageAsUserPic(imageName, context)
        return User(name, imageFile.absolutePath)
    }
}
