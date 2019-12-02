package com.example.zimtest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun Context.showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Activity.startActivityWithExtras(cls: Class<*>, extrasKey: String, extrasData: Parcelable) {
    val intent = Intent(this, cls)
    intent.putExtra(extrasKey, extrasData)
    startActivity(intent)
}

fun Context.getStringFromResources(resourceId: Int): String {
    return resources.getString(resourceId)
}

fun Fragment.getStringFromResources(resourceId: Int): String {
    return resources.getString(resourceId)
}

suspend fun <T> Call<T>.await(): T = suspendCoroutine {
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            it.resumeWithException(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                it.resume(response.body()!!)
            } else {
                it.resumeWithException(Exception())
            }
        }
    })
}

