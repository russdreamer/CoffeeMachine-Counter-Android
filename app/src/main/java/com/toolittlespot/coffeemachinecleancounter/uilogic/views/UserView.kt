package com.toolittlespot.coffeemachinecleancounter.uilogic.views

import android.content.Context
import android.net.Uri
import android.widget.ImageButton
import java.util.*

class UserView(context: Context?) : ImageButton(context){

    lateinit var name: String
    lateinit var avatarUri: Uri
    private var userId: Long = 0

    constructor(name: String, avatarUri: Uri, context: Context?) : this(context) {
        this.name = name
        this.avatarUri = avatarUri
        this.userId = Date().time
        updateAvatar()
    }

    fun updateAvatar(){
        super.setImageURI(null)
        super.setImageURI(avatarUri)
    }

    fun getUserId(): Long{
       return userId
    }
}