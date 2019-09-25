package com.clever_media.core.fonts

import android.content.res.AssetManager
import android.graphics.Typeface

/**
 * val assetManager = assets
 * FontManager.init(assetManager)
 */
object FontManager {

    private lateinit var assetManager: AssetManager
    private var fonts: MutableMap<String, Typeface> = HashMap()

    fun init(manager: AssetManager) {
        assetManager = manager
    }

    fun getFont(asset: String, ext: String) : Typeface? {
        if (fonts.contains(asset)) {
            if (fonts[asset] != null) {
                return fonts[asset]!!
            }
        }

        var font: Typeface? = null

        try {
            font = Typeface.createFromAsset(assetManager, asset)
            fonts[asset] = font
        } catch (t: Throwable) {
            t.printStackTrace()
        }

        if (font == null) {
            try {
                val fixedAsset = String.format("%s.%s", asset, ext)
                font = Typeface.createFromAsset(assetManager, fixedAsset)
                fonts[asset] = font
                fonts[fixedAsset] = font
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }

        return font
    }
}