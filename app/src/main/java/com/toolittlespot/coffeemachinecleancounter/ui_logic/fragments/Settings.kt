package com.toolittlespot.coffeemachinecleancounter.ui_logic.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.ui_logic.views.UserView

class Settings : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var inflate =  inflater.inflate(R.layout.fragment_settings, container, false)

        val users = inflate.findViewById<GridLayout>(R.id.users)

        inflate.findViewById<Button>(R.id.add_user_btn).setOnClickListener {
            var user = UserView(this.context, "Vasya", users.width / users.columnCount)
            users.addView(user, users.childCount - 1)

            user.setOnClickListener {
                println("CLICKED!!!")
            }
        }

        return inflate
    }


}
