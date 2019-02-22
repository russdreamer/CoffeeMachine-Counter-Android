package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.Views
import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils
import com.toolittlespot.coffeemachinecleancounter.businesslogic.ApplicationState
import com.toolittlespot.coffeemachinecleancounter.businesslogic.application.Achievement
import com.toolittlespot.coffeemachinecleancounter.businesslogic.dialogs.Dialogs
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity

class Settings : Fragment() {
    private lateinit var fragmentView: View
    private lateinit var addUserBtn: Button
    private lateinit var languageBtn: Button
    private lateinit var achieveBtn: Button
    private lateinit var saveButton: Button
    private lateinit var resetButton: Button
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
        configResetBtn()
        configTextViews()

        configUsesBeforeClean()
    }

    private fun configTextViews() {
        fragmentView.findViewById<TextView>(R.id.uses_before_clean_text).text = MainActivity.app.getDict(Dict.NUMBER_USE_BEFORE_CLEAN)
        fragmentView.findViewById<TextView>(R.id.who_use_txt).text = MainActivity.app.getDict(Dict.ADD_COFFEE_LOVERS)
        fragmentView.findViewById<TextView>(R.id.choose_achievement_txt).text = MainActivity.app.getDict(Dict.CHOOSE_ACHIEVEMENT)
        fragmentView.findViewById<TextView>(R.id.choose_language_btn).text = MainActivity.app.getDict(Dict.LANGUAGE)
        fragmentView.findViewById<TextView>(R.id.reset_btn).text = MainActivity.app.getDict(Dict.RESET_SETTINGS)
        fragmentView.findViewById<TextView>(R.id.save_settings_btn).text = MainActivity.app.getDict(Dict.SAVE)
    }

    private fun configResetBtn() {
        resetButton = fragmentView.findViewById(R.id.reset_btn)
        resetButton.setOnClickListener {
            val dialog = Dialogs.createBasicDialog(context!!, MainActivity.app.getDict(Dict.ARE_YOU_SURE))
            dialog.findViewById<Button>(R.id.positive_dialog_btn).setOnClickListener {
                dialog.dismiss()
                ApplicationState.removeAppState(activity as MainActivity)
            }
            dialog.show()
        }
    }

    private fun configUsesBeforeClean() {
        usesBeforeClean = fragmentView.findViewById(R.id.uses_before_clean_amount_setter)

        if (MainActivity.app.getMaxUseAmountMachine() != 0){
            usesBeforeClean.setText(MainActivity.app.getMaxUseAmountMachine().toString())
        }
    }

    private fun configSaveBtn() {
        saveButton = fragmentView.findViewById(R.id.save_settings_btn)
        saveButton.setOnClickListener {
            if (isFieldsFilled()) {
                saveSettings()
                if ((activity as MainActivity).supportFragmentManager.backStackEntryCount > 0)
                    (activity as MainActivity).onBackPressed()
                else
                    (activity as MainActivity).changeMainLayout(MainPage(), false)
            }
            else
                AppUtils().showSnackBar(fragmentView, MainActivity.app.getDict(Dict.FIELD_NOT_FILLED))
        }
    }

    private fun saveSettings() {
        val amount = Integer.valueOf(usesBeforeClean.text.toString())
        MainActivity.app.setMaxUseAmountMachine(amount, activity as MainActivity)
    }

    private fun isFieldsFilled(): Boolean {
        return ! (TextUtils.isEmpty(usesBeforeClean.text)
                || MainActivity.app.getUsers().isEmpty()
                || Integer.valueOf(usesBeforeClean.text.toString()) < 1)
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
        val usersList = MainActivity.app.getUsers()
        usersList.forEach {user->
            val userView = Views.createUserView(user, size, context)
            usersGrid.addView(userView, usersGrid.childCount - 1)

            userView.setOnClickListener{
                val uc = UserConstructor()
                uc.setUser(user)
                (activity as MainActivity).changeMainLayout(uc)
            }
        }
    }

    private fun configAddUserBtn() {
        addUserBtn = fragmentView.findViewById(R.id.add_user_btn)
        val size = AppUtils().getDevicePixelWidth(activity as MainActivity).widthPixels / 3
        Views.changeViewSize(addUserBtn, size)
        addUserBtn.setOnClickListener {
            (activity as MainActivity).changeMainLayout(UserConstructor())
        }
    }

    private fun clearTempUser() {
        AppUtils().deleteTempImage(this.context)
    }
}
