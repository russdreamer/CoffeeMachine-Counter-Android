package com.toolittlespot.coffeemachinecleancounter.businesslogic.application

import java.util.*
import kotlin.collections.LinkedHashMap

class UsersPanel {
    private var users: HashMap<Long, User> = LinkedHashMap()

    fun getUsers(): List<User> {
        return ArrayList(users.values)
    }

    fun getUser(userId: Long): User? {
        return users.get(userId)
    }

    fun addUser(user: User): User? {
        return users.put(user.getId(), user)
    }

    fun removeUser(user: User): User? {
        return users.remove(user.getId())
    }

    fun updateUser(user: User, name: String){
        user.name = name
    }
}
