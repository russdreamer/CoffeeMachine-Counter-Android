package com.toolittlespot.coffeemachinecleancounter.ui_logic.views

import android.content.Context
import android.widget.ImageButton
import com.toolittlespot.coffeemachinecleancounter.R

class UserView(context: Context?, var name: String, size: Int) : ImageButton(context) {
    init {
        super.setBackgroundResource(R.mipmap.ic_launcher)
        super.setMinimumWidth(size)
        super.setMinimumHeight(size)
    }
}