package com.toolittlespot.coffeemachinecleancounter.businesslogic.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.toolittlespot.coffeemachinecleancounter.R

class Dialogs {
    companion object {

        fun createBasicDialog(context: Context, message: String): Dialog {
            val dialog = createdDialog(context)
            createTextView(dialog, message)
            createPositiveButton("YES", dialog)
            createNegativeButton("NO", dialog)
            return dialog
        }

        private fun createTextView(dialog: Dialog, text: String): Any {
            val textView = dialog.findViewById<TextView>(R.id.dialog_message)
            textView.text = text
            return textView
        }

        private fun createdDialog(context: Context): Dialog{
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog)

            return dialog
        }

        private fun createPositiveButton(text: String, dialog: Dialog): Button{
            val button = dialog.findViewById<Button>(R.id.positive_dialog_btn)
            button.text = text
            return button
        }

        private fun createNegativeButton(text: String, dialog: Dialog): Button{
            val button = dialog.findViewById<Button>(R.id.negative_dialog_btn)
            button.setOnClickListener { dialog.dismiss() }
            button.text = text
            return button
        }
    }
}