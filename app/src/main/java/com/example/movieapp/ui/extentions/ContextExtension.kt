package com.example.movieapp.ui.extentions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Start Activity from context
 * */
inline fun <reified T : Activity> Context?.startActivity(func: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java).apply {
        func()
    }
    this?.startActivity(intent)
}

/**
 * Show Short Toast message from string resource
 * */
fun Context?.toastShort(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT) =
    this?.let { Toast.makeText(it, textId, duration).show() }

/**
 * Show Short Toast message from  a string
 * */
fun Context?.toastShort(message: String, duration: Int = Toast.LENGTH_SHORT) =
    this?.let { Toast.makeText(this, message, duration).show() }