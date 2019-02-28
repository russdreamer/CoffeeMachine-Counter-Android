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
import android.widget.*
import com.toolittlespot.coffeemachinecleancounter.CAPTURE_IMAGE
import com.toolittlespot.coffeemachinecleancounter.PICK_IMAGE
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.AppUtils
import com.toolittlespot.coffeemachinecleancounter.businesslogic.dialogs.Dialogs
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity
import com.toolittlespot.coffeemachinecleancounter.businesslogic.application.User
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
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
        configTextViews()

        configUserNameTxt()
    }

    private fun configTextViews() {
        fragmentView.findViewById<TextView>(R.id.user_name_txt).hint = MainActivity.app.getDict(Dict.NAME)
        fragmentView.findViewById<TextView>(R.id.change_photo_txt).text = MainActivity.app.getDict(Dict.SELECT_USER_IMAGE)
        fragmentView.findViewById<TextView>(R.id.gallery_txt).text = MainActivity.app.getDict(Dict.GALLERY)
        fragmentView.findViewById<TextView>(R.id.camera_txt).text = MainActivity.app.getDict(Dict.CAMERA)
        fragmentView.findViewById<TextView>(R.id.collection_txt).text = MainActivity.app.getDict(Dict.COLLECTION)
        fragmentView.findViewById<TextView>(R.id.save_user_btn).text = MainActivity.app.getDict(Dict.SAVE)
        fragmentView.findViewById<TextView>(R.id.delete_user_btn).text = MainActivity.app.getDict(Dict.DELETE)
    }

    private fun configDeleteBtn() {
        val deleteBtn = fragmentView.findViewById<Button>(R.id.delete_user_btn)
        if (this.user == null){
            deleteBtn.visibility = View.GONE
        }
        else {
            deleteBtn.setOnClickListener{
                val dialog = Dialogs.createBasicDialog(context!!, MainActivity.app.getDict(Dict.ARE_YOU_SURE))
                dialog.findViewById<Button>(R.id.positive_dialog_btn).setOnClickListener {
                    dialog.dismiss()
                    MainActivity.app.removeUser(user!!, activity as MainActivity)
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
        val saveBtn = fragmentView.findViewById<Button>(R.id.save_user_btn)
        if (this.user == null){
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                saveBtn.layoutParams.height,
                2f)

            saveBtn.layoutParams = params
        }
        saveBtn.setOnClickListener {
            if (isFieldsFilled()) {
                if (user == null){
                    val userNAme = userNameField.text.toString()
                    MainActivity.app.addUser(userNAme, activity!!)
                }
                else{
                    val userName = userNameField.text.toString()
                    MainActivity.app.updateUser(user!!, userName, activity!!)
                    (activity as MainActivity).createTabMenu()
                }
                (activity as MainActivity).onBackPressed()
            }
            else AppUtils().showSnackBar(fragmentView, MainActivity.app.getDict(Dict.FIELD_NOT_FILLED))
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
        val avaPic = cropImageToSquare(data)
        AppUtils().saveTempImage(avaPic, this.context)
    }

    private fun saveToTempImage(data: Uri?) {
        if (data != null) {
            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, data)
            saveToTempImage(bitmap)
        }
    }

    private fun cropImageToSquare(data: Bitmap, size: Int): Bitmap{
        if (data.width >= data.height){
            val longSide = (size * data.width) / data.height
            val resizedData = Bitmap.createScaledBitmap(data, longSide, size, true)
            return  Bitmap.createBitmap(
                resizedData,
                resizedData.width/2 - resizedData.height/2,
                0,
                resizedData.height,
                resizedData.height
            )

        }else {
            val longSide = (size * data.height) / data.width
            val resizedData = Bitmap.createScaledBitmap(data, size, longSide, true)
            return Bitmap.createBitmap(
                resizedData,
                0,
                resizedData.height / 2 - resizedData.width / 2,
                resizedData.width,
                resizedData.width
            )
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
