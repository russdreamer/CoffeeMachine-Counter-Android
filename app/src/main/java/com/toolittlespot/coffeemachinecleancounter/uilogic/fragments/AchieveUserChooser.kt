package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView

import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.Views
import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity


class AchieveUserChooser : Fragment() {
    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView = inflater.inflate(R.layout.fragment_achieve_user_chooser, container, false)
        configViews()

        return fragmentView
    }

    private fun configViews() {
        configTextViews()
        fillUsersGrid()
    }

    private fun configTextViews() {
        fragmentView.findViewById<TextView>(R.id.achieve_user_txt).text = MainActivity.app.getDict(Dict.CHOOSE_ACHIEVE_USER)
    }

    private fun fillUsersGrid() {
        val usersGrid = fragmentView.findViewById<GridLayout>(R.id.users)
        val size = AppUtils().getDevicePixelSize(activity as MainActivity).widthPixels / 2
        val usersList = MainActivity.app.getUsers()
        usersList.forEach {user->
            val userView = Views.createUserView(user, size, context)
            usersGrid.addView(userView)

            userView.setOnClickListener{
                AchievementChooser.selectedUser = user
                (activity as MainActivity).onBackPressed()
            }
        }
    }
}
