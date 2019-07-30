package com.

import android.app.Activity
import android.content.Intent
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showToast(text: String) {
  context?.toast(text)
}

fun Fragment.showToast(@StringRes textResId: Int) {
  context?.toast(textResId)
}

inline fun <reified T : Activity> Fragment.startActivity(preparer: Intent.() -> Unit = {}) =
  context?.startActivity<T>(preparer)
