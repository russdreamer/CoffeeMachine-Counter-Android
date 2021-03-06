package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toolbar
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.Views
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainPage : Fragment() {
    private lateinit var fragmentView: View
    private lateinit var navMenuBtn: ImageButton
    private lateinit var useBtn: Button
    private lateinit var cleanBtn: Button
    private lateinit var settingsBtn: ImageButton
    //private lateinit var toolBar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView = inflater.inflate(R.layout.fragment_main_page, container, false)
        configViews()
        checkIfClean()

        return fragmentView
    }

    private fun configViews() {
        configToolBar()
        configNavMenuBtn()
        configUseBtn()
        configSettingsBtn()
        configCleanBtn()
    }

    private fun configToolBar() {
       // toolBar = fragmentView.findViewById(R.id.toolbar_main)
    }

    private fun configCleanBtn() {
        cleanBtn = fragmentView.findViewById(R.id.clean_button)
        cleanBtn.text = MainActivity.app.getDict(Dict.CLEAN)
        cleanBtn.setOnClickListener {
            (activity as MainActivity).changeMainLayout(CleaningPersonChooser())
        }
    }

    private fun checkIfClean() {
        if (! MainActivity.app.isMachineClean()) {
            useBtn.isEnabled = false
        }
    }

    private fun configSettingsBtn() {
        settingsBtn = fragmentView.findViewById(R.id.settingsButton)
        settingsBtn.setOnClickListener {
            (activity as MainActivity).changeMainLayout(Settings())
        }
    }

    private fun configUseBtn() {
        useBtn = fragmentView.findViewById(R.id.use_button)
        useBtn.text = MainActivity.app.getDict(Dict.USE)
        useBtn.setOnClickListener {
            (activity as MainActivity).changeMainLayout(UsingPersonChooser())
        }
    }

    private fun configNavMenuBtn() {
        navMenuBtn = fragmentView.findViewById(R.id.navMenuButton)
        navMenuBtn.setOnClickListener {
            activity?.drawer_layout?.openDrawer(GravityCompat.START)
        }
    }

    override fun onResume() {
        super.onResume()
        if (MainActivity.app.getUsers().isEmpty())
            (activity as MainActivity).changeMainLayout(Settings(), false)
    }
}
