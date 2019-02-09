package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ScrollView

import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.Views
import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity

class UsingPersonChooser : Fragment() {
    private lateinit var fragmentView: View
    private lateinit var addUserBtn: Button
    private lateinit var scrollView: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView =  inflater.inflate(R.layout.fragment_using_person_chooser, container, false)
        setDialogSize()
        fillUsersGrid()
        configAddUserBtn()

        return fragmentView
    }

    private fun configAddUserBtn() {
        addUserBtn = fragmentView.findViewById(R.id.add_user_btn)
        addUserBtn.setOnClickListener {
            (activity as MainActivity).changeMainLayout(UserConstructor())
            AppUtils().deleteTempImage(this.context)
        }
    }

    private fun setDialogSize() {
        scrollView = fragmentView.findViewById(R.id.chooser_scrollView)

    }

    private fun fillUsersGrid() {
        val usersGrid = fragmentView.findViewById<GridLayout>(R.id.users)
        val size = AppUtils().getDevicePixelWidth(activity as MainActivity).widthPixels / 2
        val usersList = MainActivity.application.usersPanel.getUsers()
        usersList.forEach {user->
            val userView = Views.createUserView(user, size, context)
            usersGrid.addView(userView, usersGrid.childCount - 1)

            userView.setOnClickListener{
                MainActivity.application.coffeeMachineState.use(activity!!)
                (activity as MainActivity).onBackPressed()
            }
        }
    }


}
