package com.example.movieapp.ui.extentions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

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


/***
 * change the background color of the drawables
 */
fun Context.setVectorDrawableColor(@DrawableRes drawableResId: Int, @ColorRes colorResId: Int): Drawable? {
    val drawable = ContextCompat.getDrawable(this, drawableResId)
    drawable?.setColorFilter(ContextCompat.getColor(this, colorResId), PorterDuff.Mode.SRC_IN)
    return drawable
}