package com.toolittlespot.coffeemachinecleancounter

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import com.toolittlespot.coffeemachinecleancounter.uilogic.views.User
import java.io.File

class Views {
    companion object {
        fun changeViewSize(view: View, size: Int){
            val params = GridLayout.LayoutParams()
            params.height = size
            params.width = size
            view.layoutParams = params
        }

        fun createUserView(user: User, size: Int, context: Context?): ImageButton{
            val userView = ImageButton(context)
            userView.setImageURI(Uri.parse(user.avatarPath))
            userView.scaleType = ImageView.ScaleType.CENTER_CROP
            changeViewSize(userView, size)
            return userView
        }
    }
}