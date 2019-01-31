package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView

import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity
import com.toolittlespot.coffeemachinecleancounter.uilogic.views.UserView

class AddUser : Fragment() {
    private lateinit var fragmentView: View
    private lateinit var newUser: UserView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView =  inflater.inflate(R.layout.fragment_add_user, container, false)
        createDraftUser()
        configButtons()

        return fragmentView
    }

    private fun createDraftUser() {
        val app = (activity as MainActivity).application
        if (app.tempUser == null){
            app.tempUser = UserView(this.context)
            newUser = app.tempUser!!
        }
    }

    private fun configButtons() {
        configCollectionBtn()
        configCameraBtn()
    }

    private fun configCameraBtn() {
        fragmentView.findViewById<ImageButton>(R.id.camera_btn).setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 0)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data?.extras != null) {
            val bitmap = data.extras?.get("data") as Bitmap
            fragmentView.findViewById<ImageView>(R.id.user_avatar_img).setImageBitmap(bitmap)
        }
    }

    private fun configCollectionBtn() {
        fragmentView.findViewById<ImageButton>(R.id.collection_btn).setOnClickListener{
            (activity as MainActivity).changeMainLayout(AvatarGallery())
        }
    }

    override fun onResume() {
        super.onResume()
        updateAvatar()
    }

    private fun updateAvatar() {
        if (newUser.avatarResource != 0)
            fragmentView.findViewById<ImageView>(R.id.user_avatar_img).setImageResource(newUser.avatarResource)
    }
}
