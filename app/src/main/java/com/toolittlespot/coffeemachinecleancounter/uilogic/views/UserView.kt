package com.toolittlespot.coffeemachinecleancounter.uilogic.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageButton
import com.toolittlespot.coffeemachinecleancounter.R

class UserView(context: Context?) : ImageButton(context){

    lateinit var name: String
    lateinit var avatarUri: Uri

    constructor(name: String, avatarUri: Uri, size: Int, context: Context?) : this(context) {
        this.name = name
        this.avatarUri = avatarUri
        configButton(size)
    }

    fun configButton(size: Int){
        super.setImageURI(avatarUri)
        super.setMinimumWidth(size)
        super.setMinimumHeight(size)
        setOnClickListener(null)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        println("CLICKED!!")
    }



}