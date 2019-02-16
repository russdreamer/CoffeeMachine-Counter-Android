package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import com.toolittlespot.coffeemachinecleancounter.CAPTURE_IMAGE
import com.toolittlespot.coffeemachinecleancounter.PICK_IMAGE
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils
import com.toolittlespot.coffeemachinecleancounter.businesslogic.dialogs.Dialogs
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity
import com.toolittlespot.coffeemachinecleancounter.businesslogic.application.User
import java.io.File

class UserConstructor : Fragment() {
    private lateinit var fragmentView: View
    private lateinit var userNameField: EditText
    private var user: User? = null

    fun setUser(existingUserView: User){
        user = existingUserView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView =  inflater.inflate(R.layout.fragment_user_constructor, container, false)
        configViews()
        fillFields()

        return fragmentView
    }

    private fun fillFields() {
        if (user != null){
            userNameField.setText(user!!.name)
            if (!AppUtils().getTempImageFile(context).exists()) {
                saveToTempImage(Uri.fromFile(File(user!!.avatarPath)))
            }
        }
    }

    private fun configViews() {
        configCollectionBtn()
        configCameraBtn()
        configGalleryBtn()
        configSaveBtn()
        configDeleteBtn()

        configUserNameTxt()
    }

    private fun configDeleteBtn() {
        val deleteBtn = fragmentView.findViewById<Button>(R.id.delete_user_btn)
        if (this.user == null){
            deleteBtn.visibility = View.GONE
        }
        else {
            deleteBtn.setOnClickListener{
                val dialog = Dialogs.createBasicDialog(context!!, "Are you sure?")
                dialog.findViewById<Button>(R.id.positive_dialog_btn).setOnClickListener {
                    dialog.dismiss()
                    MainActivity.application.removeUser(user!!, activity as MainActivity)
                    (activity as MainActivity).onBackPressed()
                }
                dialog.show()
            }
        }
    }

    private fun configUserNameTxt() {
        userNameField = fragmentView.findViewById(R.id.user_name_txt)
    }

    private fun configSaveBtn() {
        fragmentView.findViewById<Button>(R.id.save_user_btn).setOnClickListener {
            if (isFieldsFilled()) {
                if (user == null){
                    val userNAme = userNameField.text.toString()
                    MainActivity.application.addUser(userNAme, activity!!)
                }
                else{
                    val userName = userNameField.text.toString()
                    MainActivity.application.updateUser(user!!, userName, activity!!)
                }
                (activity as MainActivity).onBackPressed()
            }
            else AppUtils().showSnackBar(fragmentView, "Заполните все параметры пользователя!")
        }
    }

    private fun isFieldsFilled(): Boolean {
        return ! TextUtils.isEmpty(userNameField.text) && AppUtils().getTempImageFile(context).exists()
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
            saveToTempImage(bitmap)
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
