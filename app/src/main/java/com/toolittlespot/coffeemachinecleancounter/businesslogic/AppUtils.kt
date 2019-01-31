package com.toolittlespot.coffeemachinecleancounter.businesslogic

import android.support.design.widget.Snackbar
import android.view.View
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
}