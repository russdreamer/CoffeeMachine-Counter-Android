package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ScrollView
import android.widget.TextView

import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.Views
import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity

class CleaningPersonChooser : Fragment() {

    private lateinit var fragmentView: View
    private lateinit var scrollView: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView =  inflater.inflate(R.layout.fragment_cleaning_person_chooser, container, false)
        setDialogSize()
        configTextViews()
        fillUsersGrid()

        return fragmentView
    }

    private fun configTextViews() {
        fragmentView.findViewById<TextView>(R.id.who_clean_txt).text = MainActivity.app.getDict(Dict.WHO_CLEAN)
    }

    private fun setDialogSize() {
        scrollView = fragmentView.findViewById(R.id.chooser_scrollView)

    }

    private fun fillUsersGrid() {
        val usersGrid = fragmentView.findViewById<GridLayout>(R.id.users)
        val size = AppUtils().getDevicePixelSize(activity as MainActivity).widthPixels / 2

        val addUserBtn = Views.createAddUserBtn(size, activity!!)
        usersGrid.addView(addUserBtn)

        val usersList = MainActivity.app.getUsers()
        usersList.forEach {user->
            val userView = Views.createUserView(user, size, context)
            usersGrid.addView(userView, usersGrid.childCount - 1)

            userView.setOnClickListener{
                val wasDirty = MainActivity.app.isMachineClean().not()
                MainActivity.app.cleanCoffeeMachine(user, activity!!)
                AppUtils().showSnackBar(fragmentView, MainActivity.app.getDict(Dict.COFFEE_MACHINE_CLEAN))
                (activity as MainActivity).onBackPressed()

                if (wasDirty){
                    MainActivity.app.reachAchievement(activity!!)
                    (activity as MainActivity).changeMainLayout(AchievementChooser())
                }
            }
        }
    }
}
