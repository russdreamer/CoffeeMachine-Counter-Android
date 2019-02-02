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
import android.widget.ImageButton
import android.widget.ImageView
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity
import com.toolittlespot.coffeemachinecleancounter.uilogic.views.UserView
import kotlinx.android.synthetic.main.nav_header_main.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class AddUser : Fragment() {
    private lateinit var fragmentView: View
    private lateinit var newUser: UserView
    private val CAPTURE_IMAGE: Int = 0
    private val PICK_IMAGE: Int = 100
    private val USERS_IMAGE_FOLDER: String = "users_images"
    private val TEMP_IMAGE: String = "temp_user_image.jpg"

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
        configGalleryBtn()
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
            when(requestCode) {
                PICK_IMAGE -> setAvatarImage(data?.data)
                CAPTURE_IMAGE -> setAvatarImage(data?.extras?.get("data") as Bitmap)
            }
        }
    }

    private fun setAvatarImage(data: Bitmap) {
        newUser.avatarResource = BitmapDrawable(resources, data)
    }

    private fun setAvatarImage(data: Uri?) {
        if (data != null) {
            val drawable = getDrawableFromUri(data)
            newUser.avatarResource = drawable
        }
    }

    private fun getDrawableFromUri(uri: Uri): Drawable {
        var drawable: Drawable
        try {
            val inputStream = activity?.contentResolver?.openInputStream(uri)
            drawable = Drawable.createFromStream(inputStream, uri.toString())
        } catch (e: FileNotFoundException) {
            drawable = resources.getDrawable(R.drawable.avatar_incognito)
        }
        return drawable
    }

    private fun saveBitmapToFile(bitmap: Bitmap, imageName: String) {
        val cw = ContextWrapper(this.context)
        val directory = cw.getDir(USERS_IMAGE_FOLDER, Context.MODE_PRIVATE)
        val imagePath = File(directory, imageName)

        val out = FileOutputStream(imagePath)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.flush()
        out.close()
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
        if (newUser.avatarResource != null)
            fragmentView.findViewById<ImageView>(R.id.user_avatar_img).setImageDrawable(newUser.avatarResource)
    }
}
