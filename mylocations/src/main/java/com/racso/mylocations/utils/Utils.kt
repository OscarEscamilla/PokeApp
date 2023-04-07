package com.racso.mylocations.utils

import android.app.AlertDialog
import android.content.Context
import java.util.*


fun showSimpleAlert(context: Context, msg: String) {
    AlertDialog.Builder(context)
        .setMessage(msg)
        .setPositiveButton("OK") { dialog, id -> }
        .show()
}


fun getUuid(): String {
    return UUID.randomUUID().toString().replace("-","")
}


