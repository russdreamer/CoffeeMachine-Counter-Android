package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.ApplicationState
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity


class Language : Fragment() {
    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView =  inflater.inflate(R.layout.fragment_language, container, false)
        configViews()

        return fragmentView
    }

    private fun configViews() {
        configRusLangBtn()
        configEngLangBtn()
    }

    private fun configEngLangBtn() {
        fragmentView.findViewById<Button>(R.id.english_language).setOnClickListener {
            MainActivity.app.changeLanguage("en")
            ApplicationState.saveAppState(activity!!)
            (activity as MainActivity).createTabMenu()
            (activity as MainActivity).onBackPressed()
        }
    }

    private fun configRusLangBtn() {
        fragmentView.findViewById<Button>(R.id.russian_language).setOnClickListener {
            MainActivity.app.changeLanguage("ru")
            ApplicationState.saveAppState(activity!!)
            (activity as MainActivity).createTabMenu()
            (activity as MainActivity).onBackPressed()
        }
    }


}
