package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils
import com.toolittlespot.coffeemachinecleancounter.businesslogic.Application
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainPage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var inflate = inflater.inflate(R.layout.fragment_main_page, container, false)

        inflate.findViewById<ImageButton>(R.id.navMenuButton).setOnClickListener {
            activity?.drawer_layout?.openDrawer(GravityCompat.START)
        }
        inflate.findViewById<Button>(R.id.use_button).setOnClickListener {
            AppUtils().showSnackBar(inflate, "clicked")
        }
        inflate.findViewById<ImageButton>(R.id.settingsButton).setOnClickListener {
            (activity as MainActivity).changeMainLayout(Settings())
        }

        return inflate;
    }

    override fun onResume() {
        super.onResume()
        if (MainActivity.application.users.isEmpty())
            (activity as MainActivity).changeMainLayout(Settings(), false)
    }
}
