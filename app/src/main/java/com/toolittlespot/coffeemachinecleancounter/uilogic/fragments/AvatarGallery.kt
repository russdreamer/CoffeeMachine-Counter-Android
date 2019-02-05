package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView

import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.Views
import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity


class AvatarGallery : Fragment() {
    lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView = inflater.inflate(R.layout.fragment_avatar_galery, container, false)
        fillGallery()
        setBackBtnListener()

        return fragmentView
    }

    private fun setBackBtnListener() {
        fragmentView.findViewById<Button>(R.id.back_btn)?.setOnClickListener{
            (activity as MainActivity).onBackPressed()
        }
    }

    private fun fillGallery() {
        val avatarsGrid = fragmentView.findViewById<GridLayout>(R.id.avatars)
        val size = AppUtils().getDevicePixelWidth(activity as MainActivity).widthPixels / 3

        for (i in 1..16){
            val srcName = "avatar_$i"
            val resID = resources.getIdentifier(srcName, "drawable", activity?.packageName)
            val image = createAvatar(this.context, resID, size)

            avatarsGrid.addView(image)
        }
    }

    private fun createAvatar(context: Context?, imageResource: Int, size: Int): View? {
        val img = ImageButton(context)
        img.setImageResource(imageResource)
        img.scaleType = ImageView.ScaleType.CENTER_CROP

        Views.changeViewSize(img, size)

        img.setOnClickListener {
            val bitmap = BitmapFactory.decodeResource(resources, imageResource)
            AppUtils().saveTempImage(bitmap, context)
            (activity as MainActivity).onBackPressed()
        }
        return img
    }


}
