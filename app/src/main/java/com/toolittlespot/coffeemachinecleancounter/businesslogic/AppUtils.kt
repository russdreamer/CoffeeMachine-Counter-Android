package com.toolittlespot.coffeemachinecleancounter.businesslogic

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import android.support.design.widget.Snackbar
import android.util.DisplayMetrics
import android.view.View
import com.toolittlespot.coffeemachinecleancounter.TEMP_IMAGE
import com.toolittlespot.coffeemachinecleancounter.USERS_IMAGE_FOLDER
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity
import java.io.File
import java.io.FileOutputStream
import java.util.*

class AppUtils {

    /**
     * get user's system language
     * @return user's system language
     */
    fun getLocalLanguage(): String {
        return Locale.getDefault().language
    }

    fun showSnackBar(view: View, text: String){
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    fun deleteTempImage(context: Context?){
        val image = getTempImageFile(context)

        if (image.exists()){
            image.delete()
        }
    }

    fun saveTempImage(bitmap: Bitmap, context: Context?): Boolean {
        val image = getTempImageFile(context)

        val out = FileOutputStream(image)
        val res = bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.flush()
        out.close()

        return res
    }

    fun saveTempImageAsUserPic(imageName: String, context: Context?): File{
        val directory = getApplicationFolder(USERS_IMAGE_FOLDER, context)
        return getTempImageFile(context).copyTo(File(directory, imageName), true)
    }

    private fun getApplicationFolder(folderName: String, context: Context?): File {
        val cw = ContextWrapper(context)
        return cw.getDir(folderName, Context.MODE_PRIVATE)

    }

    fun getTempImageFile(context: Context?): File {
        val directory = getApplicationFolder(USERS_IMAGE_FOLDER, context)
        return File(directory, TEMP_IMAGE)
    }

    fun getDevicePixelSize(activity: Activity): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }
}