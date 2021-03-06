package com.toolittlespot.coffeemachinecleancounter.uilogic

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.Application
import com.toolittlespot.coffeemachinecleancounter.businesslogic.ApplicationState
import com.toolittlespot.coffeemachinecleancounter.businesslogic.dialogs.Dialogs
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.History
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.MainPage
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.Settings
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.Statistics
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var pageAdapter: SectionPageAdapter
    private lateinit var viewPager: ViewPager

    companion object {
        lateinit var app: Application
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startApplication()
        makeFullScreen()
        setContentView(R.layout.activity_main)
        createActionBar()
        supportActionBar!!.hide()
    }

    private fun startApplication() {
        val appState = ApplicationState.loadAppState(this)
        if (appState != null) {
            MainActivity.app = appState
            changeMainLayout(MainPage())
        } else {
            MainActivity.app = Application()
            changeMainLayout(Settings(), false)
        }
    }

    private fun makeFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        createTabMenu()

        return true
    }

    fun refreshFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().detach(fragment).attach(fragment).commit()
    }

    private fun createActionBar() {
        this.setSupportActionBar(toolbar)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionPageAdapter(supportFragmentManager)
        adapter.addFragment(Statistics(), app.getDict(Dict.STATISTICS))
        adapter.addFragment(History(), app.getDict(Dict.HISTORY))

        viewPager.adapter = adapter
    }

    fun changeMainLayout(newLayout: Fragment, addToBackStack: Boolean = true) {
        val fragmentManager = supportFragmentManager

        val transaction = fragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            .replace(
            R.id.mainFragment,
            newLayout
        )
        if (addToBackStack)
            transaction.addToBackStack(null)

        transaction.commit()
    }

    fun createTabMenu() {
        pageAdapter = SectionPageAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.container)
        setupViewPager(viewPager)

        val tabLayout: TabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        val cleanBtn = findViewById<Button>(R.id.cleanHistoryButton)
        cleanBtn.text = app.getDict(Dict.CLEAN_HISTORY)
        cleanBtn.setOnClickListener {
            val dialog = Dialogs.createBasicDialog(this, app.getDict(Dict.ARE_YOU_SURE))
            dialog.findViewById<Button>(R.id.positive_dialog_btn).setOnClickListener {
                dialog.dismiss()
                MainActivity.app.clearHistory(this)
            }
            dialog.show()
        }
    }
}