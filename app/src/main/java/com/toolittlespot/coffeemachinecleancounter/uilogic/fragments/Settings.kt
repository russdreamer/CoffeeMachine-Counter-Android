package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.Views
import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity

class Settings : Fragment() {
    private lateinit var fragmentView: View
    private lateinit var addUserBtn: Button
    private lateinit var languageBtn: Button
    private lateinit var saveButton: Button
    private lateinit var usesBeforeClean: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView = inflater.inflate(R.layout.fragment_settings, container, false)
        clearTempUser()
        configViews()
        fillUsersGrid()
        return fragmentView
    }

    private fun configViews() {
        configAddUserBtn()
        configLanguageBtn()
        configSaveBtn()

        configUsesBeforeClean()
    }

    private fun configUsesBeforeClean() {
        usesBeforeClean = fragmentView.findViewById(R.id.uses_before_clean_amount_setter)
    }

    private fun configSaveBtn() {
        saveButton = fragmentView.findViewById(R.id.save_settings_btn)
        saveButton.setOnClickListener {
            if (isFieldsFilled())
                if ( (activity as MainActivity).supportFragmentManager.backStackEntryCount > 0 )
                    (activity as MainActivity).onBackPressed()
                else
                    (activity as MainActivity).changeMainLayout(MainPage(), false)
            else
                AppUtils().showSnackBar(fragmentView, "Заполните все параметры!")
        }
    }

    private fun isFieldsFilled(): Boolean {
        return ! TextUtils.isEmpty(usesBeforeClean.text) && (activity as MainActivity).application.users.size > 0
    }

    private fun configLanguageBtn() {
        languageBtn = fragmentView.findViewById(R.id.choose_language_btn)
        languageBtn.setOnClickListener{
            (activity as MainActivity).changeMainLayout(Language())
        }
    }

    private fun fillUsersGrid() {
        val usersGrid = fragmentView.findViewById<GridLayout>(R.id.users)
        val size = AppUtils().getDevicePixelWidth(activity as MainActivity).widthPixels / 3
        val usersList = (activity as MainActivity).application.users.values
        usersList.forEach {userView->
            usersGrid.addView(userView, usersGrid.childCount - 1)
            userView.scaleType = ImageView.ScaleType.CENTER_CROP
            Views().changeViewSize(userView, size)
            userView.setOnClickListener{
                var uc = UserConstructor()
                uc.setUserView(userView)
                (activity as MainActivity).changeMainLayout(uc)
            }
        }
    }

    private fun configAddUserBtn() {
        addUserBtn = fragmentView.findViewById(R.id.add_user_btn)
        Views().changeViewSize(addUserBtn, AppUtils().getDevicePixelWidth(activity as MainActivity).widthPixels / 3)
        addUserBtn.setOnClickListener {
            (activity as MainActivity).changeMainLayout(UserConstructor())
        }
    }

    private fun clearTempUser() {
        AppUtils().deleteTempImage(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentView.findViewById<GridLayout>(R.id.users).removeAllViews()
    }
}
