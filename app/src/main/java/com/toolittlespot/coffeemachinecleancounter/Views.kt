package com.toolittlespot.coffeemachinecleancounter

import android.view.View
import android.widget.GridLayout

class Views {
    fun changeViewSize(view: View, size: Int){
        val params = GridLayout.LayoutParams()
        params.height = size
        params.width = size
        view.layoutParams = params
    }
}