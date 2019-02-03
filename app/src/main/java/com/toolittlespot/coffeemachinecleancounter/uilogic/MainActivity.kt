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
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.Application
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.LangMap
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.History
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.MainPage
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.Settings
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.Statistics
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var pageAdapter: SectionPageAdapter
    private lateinit var viewPager: ViewPager
    lateinit var application: Application

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startApplication()
        makeFullScreen()
        setContentView(R.layout.activity_main)
        createActionBar()
        supportActionBar!!.hide()
    }

    private fun startApplication() {
        //if no saved app state orx
        application = Application()
        changeMainLayout(Settings(), false)
        //else
        //changeMainLayout(MainPage())
    }

    private fun makeFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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

    private fun createActionBar() {
        this.setSupportActionBar(toolbar)
    }

    fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionPageAdapter(supportFragmentManager)
        adapter.addFragment(Statistics(), LangMap().getDict(Dict.STATISTICS))
        adapter.addFragment(History(), LangMap().getDict(Dict.HISTORY))

        viewPager.adapter = adapter
    }

    fun changeMainLayout(newLayout: Fragment, addToBackStack: Boolean = true) {
        val fragmentManager = supportFragmentManager

        var transaction = fragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            .replace(
            R.id.mainFragment,
            newLayout
        )
        if (addToBackStack)
            transaction.addToBackStack(null)

        transaction.commit()
    }

    private fun createTabMenu() {
        pageAdapter = SectionPageAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.container)
        setupViewPager(viewPager)

        val tabLayout: TabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
    }
}