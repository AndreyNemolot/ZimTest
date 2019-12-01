package com.example.zimtest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.widget.Toast

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Activity.startActivityWithExtras(cls: Class<*>, extrasKey: String, extrasData: Parcelable){
    val intent = Intent(this, cls)
    intent.putExtra(extrasKey, extrasData)
    startActivity(intent)
}