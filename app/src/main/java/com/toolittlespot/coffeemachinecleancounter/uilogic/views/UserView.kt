package com.toolittlespot.coffeemachinecleancounter.uilogic.views

import android.content.Context
import android.widget.ImageButton
import com.toolittlespot.coffeemachinecleancounter.R

class UserView(context: Context?) : ImageButton(context){

    lateinit var name: String
    var avatarResource: Int = 0

    fun init(size: Int){
        super.setBackgroundResource(R.mipmap.ic_launcher)
        super.setMinimumWidth(size)
        super.setMinimumHeight(size)
        setOnClickListener(null)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        println("CLICKED!!")
    }
}