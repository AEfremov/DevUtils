package com.

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import android.text.Html
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.View
import android.webkit.URLUtil
import androidx.fragment.app.Fragment
import com.clever_media.R

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.drawable(drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.visibilityINVISIBLE(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

//fun Context.convertDpToPx(dp: Float): Int {
//    val resources = resources
//    val metrics = resources.displayMetrics
//    return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
//}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun convertDpToPixel(dp: Float, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun isValidEmail(target: String) : Boolean =
        if (TextUtils.isEmpty(target)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }

fun fromHtml(target: String) : String =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(target, Html.FROM_HTML_MODE_LEGACY).toString().trim()
        } else {
            Html.fromHtml(target).toString().trim()
        }

fun String.removeImgTagFromHtml() : String = replace("<img.+?>".toRegex(), "")

fun Context.share(shareBody: String) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
    startActivity(Intent.createChooser(sharingIntent, this.resources.getString(R.string.action_share)))
}

fun Fragment.tryOpenLink(link: String, basePath: String? = "https://google.com/search?q=") {
    startActivity(
        Intent(
            Intent.ACTION_VIEW,
            when {
                URLUtil.isValidUrl(link) -> Uri.parse(link)
                else -> Uri.parse(basePath + link)
            }
        )
    )
}

fun Fragment.phoneIntent(number: String) {
    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")))
}
