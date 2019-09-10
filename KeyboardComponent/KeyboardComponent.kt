package com.

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.component_keyboard.view.*

class KeyboardComponent : LinearLayout {

    private lateinit var view: View
    private lateinit var inputView: TextView
    private lateinit var watcher: FormatWatcher
    private lateinit var pinsView: LinearLayout
    private var isPinAttached: Boolean = false
    private lateinit var listener: OnNextListener

    constructor(context: Context?) : super(context) {
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context?) {
        view = LayoutInflater.from(context).inflate(R.layout.component_keyboard, this, true)

        view.keyboardOne.setOnClickListener { append("1") }
        view.keyboardTwo.setOnClickListener { append("2") }
        view.keyboardThree.setOnClickListener { append("3") }
        view.keyboardFour.setOnClickListener { append("4") }
        view.keyboardFive.setOnClickListener { append("5") }
        view.keyboardSix.setOnClickListener { append("6") }
        view.keyboardSeven.setOnClickListener { append("7") }
        view.keyboardEight.setOnClickListener { append("8") }
        view.keyboardNine.setOnClickListener { append("9") }
        view.keyboardZero.setOnClickListener { append("0") }
        view.keyboardClear.setOnClickListener { clear() }
        view.keyboardDelete.setOnClickListener { delete() }
    }

    fun setInput(inputView: TextView) {
        this.inputView = inputView
    }

    fun setWatcher(watcher: FormatWatcher) {
        this.watcher = watcher
    }

    fun setPins(pinsView: LinearLayout) {
        this.pinsView = pinsView
        this.isPinAttached = true
    }

    private fun append(number: String) {
        if (isPinAttached) {
            appendPin(number)
        } else {
            inputView.append(number)
        }
    }

    private fun appendPin(number: String) {
        for (i in 0 until pinsView.childCount) {
            val pinControl: PinComponent = pinsView.getChildAt(i) as PinComponent
            if (!pinControl.isFilled()) {
                pinControl.setFilled(true)
                pinControl.tag = number

                if ((pinsView.getChildAt(3) as PinComponent).isFilled()) {
                    listener.next()
                }

                break
            }
        }
    }

    fun clear() {
        if (isPinAttached) {
            clearPin()
        } else {
            inputView.text = ""
        }
    }

    private fun clearPin() {
        (pinsView.childCount - 1 downTo 0)
                .map { pinsView.getChildAt(it) as PinComponent }
                .forEach { it.setFilled(false) }
        return
    }

    private fun delete() {
        if (isPinAttached) {
            deletePin()
        } else if (getValue().isNotEmpty()) {
            deleteNumber()
        }
    }

    private fun deletePin() {
        for (i in pinsView.childCount - 1 downTo 0) {
            val pinControl: PinComponent = pinsView.getChildAt(i) as PinComponent
            if (pinControl.isFilled()) {
                pinControl.setFilled(false)
                break
            }
        }
    }

    private fun deleteNumber() {
        var phone: String = getValue()
        phone = phone.substring(0, getValue().length - 1)
        inputView.text = phone
    }

    fun getValue(): String {
        if (isPinAttached) {
            var pin = ""
            for (i in 0 until pinsView.childCount) {
                pin += pinsView.getChildAt(i).tag
            }
            return pin
        } else if (watcher.isInstalled) {
            return getUnformattedNumber()
        }
        return inputView.text.toString()
    }

    private fun getUnformattedNumber(): String {
        return watcher.mask.toUnformattedString()
                .replace(" ", "")
                .replace("-", "")
    }

    fun setNextListener(listener: OnNextListener) {
        this.listener = listener
    }

    interface OnNextListener {
        fun next()
    }
}
