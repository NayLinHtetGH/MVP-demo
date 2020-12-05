package com.naylinhtet.mvpwithretrofit.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object ShowDialog {

    fun showEmptyDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
        val dialog: AlertDialog = builder.create()

//        if (!(context as Activity).isFinishing) {
            dialog.show()
//        }
    }

}