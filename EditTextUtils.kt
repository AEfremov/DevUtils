package com.ltech.moneyto.core.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat
import android.widget.EditText

class EditTextUtils(private val context: Context) {

    @Suppress("DEPRECATION")
    fun setColor(view: EditText, color: Int) {
        val originalDrawable = view.background
        originalDrawable.mutate()
        val wrappedDrawable: Drawable = DrawableCompat.wrap(originalDrawable)
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(context.resources.getColor(color)))
        view.background = wrappedDrawable
    }
}