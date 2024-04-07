package com.hgokumus.cryptoapp.core.extensions

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import com.hgokumus.cryptoapp.R

fun Context.showSuccessDialog(@StringRes message: Int) {
    val dialogBuilder = AlertDialog.Builder(this)
    with(dialogBuilder) {
        setTitle(R.string.crypto_dialog_success_title)
        setMessage(message)
        setPositiveButton("OK") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        val alertDialog = create()
        alertDialog.show()
    }
}

fun Context.showErrorDialog(@StringRes message: Int) {
    val dialogBuilder = AlertDialog.Builder(this)
    with(dialogBuilder) {
        setTitle(R.string.crypto_dialog_error_title)
        setMessage(message)
        setPositiveButton("OK") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        val alertDialog = create()
        alertDialog.show()
    }
}