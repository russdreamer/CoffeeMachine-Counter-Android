package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity

class AchieveMessageChooser : Fragment() {
    private lateinit var fragmentView: View
    private lateinit var secretMessage: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView = inflater.inflate(R.layout.fragment_achieve_message_chooser, container, false)
        configViews()

        return fragmentView
    }

    private fun configViews() {
        configTextViews()
        configOkButton()
    }

    private fun configOkButton() {
        fragmentView.findViewById<Button>(R.id.message_ok_btn).setOnClickListener {
            AchievementChooser.selectedAchievement?.message = secretMessage.text.toString()
            (activity as MainActivity).onBackPressed()
        }
    }

    private fun configTextViews() {
        fragmentView.findViewById<TextView>(R.id.secret_message_txt).text = MainActivity.app.getDict(Dict.TYPE_SECRET_MESSAGE)
        secretMessage = fragmentView.findViewById(R.id.secret_message)
        secretMessage.hint = MainActivity.app.getDict(Dict.SECRET_MESSAGE_HINT)
    }


}
