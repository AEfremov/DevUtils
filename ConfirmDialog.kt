package com.

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog

/**
 * 
 * ConfirmDialog
 *       .newInstance(
 *           title = null,
 *           msg = "Message text",
 *           positive = "Next",
 *           negative = null,
 *           tag = TAG_NEED_DOCUMENT)
 *       .show(supportFragmentManager, TAG_NEED_DOCUMENT)
 *
 * Declare ConfirmDialog.OnClickListener
 *
 * override fun dialogConfirm(tag: String) {
 *   when (tag) {
 *   TAG_NEED_DOCUMENT -> { ... }
 * 
 */
class ConfirmDialog : androidx.fragment.app.DialogFragment() {
  
  private var clickListener: OnClickListener? = null
  
  private val title: String? get() = arguments?.getString(TITLE)
  private val msg: String get() = arguments?.getString(MSG) ?: ""
  private val positive: String get() = arguments?.getString(POSITIVE_TEXT) ?: "Ok"
  private val negative: String get() = arguments?.getString(NEGATIVE_TEXT) ?: /*"Cancel"*/""
  private val dialogTag: String get() = arguments?.getString(TAG) ?: "ConfirmDialog tag"
  
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
      AlertDialog.Builder(context!!).apply {
        title?.let { setTitle(title) }
        if (msg.isNotEmpty()) { setMessage(msg) }
        setPositiveButton(positive) { _, _ -> clickListener?.dialogConfirm(dialogTag) }
        setNegativeButton(negative) { _, _ -> dismiss() }
        isCancelable = false
      }.create()
  
  override fun onAttach(context: Context?) {
    super.onAttach(context)
    
    clickListener = when {
      parentFragment is OnClickListener -> parentFragment as OnClickListener
      activity is OnClickListener -> activity as OnClickListener
      else -> null
    }
  }
  
  override fun onDetach() {
    clickListener = null
    super.onDetach()
  }
  
  companion object {
    private const val TITLE = "title"
    private const val MSG = "msg"
    private const val POSITIVE_TEXT = "positive_text"
    private const val NEGATIVE_TEXT = "negative_text"
    private const val TAG = "tag"
    
    fun newInstance(
        title: String? = null,
        msg: String? = null,
        positive: String? = null,
        negative: String? = null,
        tag: String
    ) =
        ConfirmDialog().apply {
          arguments = Bundle().apply {
            putString(TITLE, title)
            putString(MSG, msg)
            putString(POSITIVE_TEXT, positive)
            putString(NEGATIVE_TEXT, negative)
            putString(TAG, tag)
          }
        }
  }
  
  interface OnClickListener {
    fun dialogConfirm(tag: String)
  }
}