package com.toolittlespot.coffeemachinecleancounter.ui_logic

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionPageAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val fragmentList = arrayListOf<Fragment>()
    private val fragmentTitleList= arrayListOf<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }
}
