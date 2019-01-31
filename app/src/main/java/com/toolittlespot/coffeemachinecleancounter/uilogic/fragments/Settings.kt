package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity

class Settings : Fragment() {
    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView = inflater.inflate(R.layout.fragment_settings, container, false)
        configAddUserBtn()
        fillUsersGrid()

        return fragmentView
    }

    private fun fillUsersGrid() {
        val usersGrid = fragmentView.findViewById<GridLayout>(R.id.users)
        val usersList = (activity as MainActivity).application.users
        for (i in 0..(usersList.size - 1))
            usersGrid.addView(usersList[i], usersGrid.childCount - 1)
    }

    private fun configAddUserBtn() {
        fragmentView.findViewById<Button>(R.id.add_user_btn).setOnClickListener {
            clearTempUser()
            (activity as MainActivity).changeMainLayout(AddUser())
        }
    }

    private fun clearTempUser() {
        (activity as MainActivity).application.tempUser = null
    }


}
