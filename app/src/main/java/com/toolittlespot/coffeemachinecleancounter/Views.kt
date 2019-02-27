package com.toolittlespot.coffeemachinecleancounter

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils
import com.toolittlespot.coffeemachinecleancounter.businesslogic.application.User
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.UserConstructor

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

        fun createAddUserBtn(size: Int, activity: Activity): View? {
            val btn = ImageButton(activity)
            btn.setImageResource(R.drawable.add_user_btn)

            btn.scaleType = ImageView.ScaleType.CENTER_CROP
            btn.setOnClickListener {
                (activity as MainActivity).changeMainLayout(UserConstructor())
                AppUtils().deleteTempImage(activity)
            }
            Views.changeViewSize(btn, size)
            return btn
        }
    }
}