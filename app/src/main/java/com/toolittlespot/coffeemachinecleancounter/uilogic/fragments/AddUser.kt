package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.toolittlespot.coffeemachinecleancounter.CAPTURE_IMAGE
import com.toolittlespot.coffeemachinecleancounter.PICK_IMAGE
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity
import com.toolittlespot.coffeemachinecleancounter.uilogic.views.UserView
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.*

class AddUser : Fragment() {
    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView =  inflater.inflate(R.layout.fragment_add_user, container, false)
        configButtons()

        return fragmentView
    }

    private fun configButtons() {
        configCollectionBtn()
        configCameraBtn()
        configGalleryBtn()
        configSaveBtn()
    }

    private fun configSaveBtn() {
        fragmentView.findViewById<Button>(R.id.save_user_btn).setOnClickListener {
            /*if (isTempUserFieldFilled()) {
                val imageName = Date().time.toString()
                if ( saveDrawableToFile(newUser.avatarResource, imageName) ){
                    val user = UserView()
                    (activity as MainActivity).application.users.add()
                }
            }*/
        }
    }

    private fun configGalleryBtn() {
        fragmentView.findViewById<ImageButton>(R.id.gallery_btn).setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }
    }

    private fun configCameraBtn() {
        fragmentView.findViewById<ImageButton>(R.id.camera_btn).setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAPTURE_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK){
            fragmentView.findViewById<ImageView>(R.id.user_avatar_img).setImageDrawable(null)
            when(requestCode) {
                PICK_IMAGE -> saveToTempImage(data?.data)
                CAPTURE_IMAGE -> saveToTempImage(data?.extras?.get("data") as Bitmap)
            }
        }
    }

    private fun saveToTempImage(data: Bitmap) {
        AppUtils().saveTempImage(data, this.context)
    }

    private fun saveToTempImage(data: Uri?) {
        if (data != null) {
            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, data)
            AppUtils().saveTempImage(bitmap, this.context)
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
        val tempImage = AppUtils().getTempImageFile(context)
        if (tempImage.exists()){
            fragmentView.findViewById<ImageView>(R.id.user_avatar_img).setImageURI(Uri.fromFile(tempImage))
        }
    }
}
