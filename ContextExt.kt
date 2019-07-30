package com.

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.toast(messageResId: Int, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this, messageResId, duration).show()

inline fun <reified T : Activity> Context.startActivity(preparer: Intent.() -> Unit = {}) {
    startActivity(intent<T>(preparer))
}

inline fun <reified T : Any> Context.intent(preparer: Intent.() -> Unit = {}): Intent =
    Intent(this, T::class.java).apply(preparer)
