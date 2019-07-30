package com.

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun <VH: RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.inflateView(@LayoutRes layoutRes: Int, parent: ViewGroup): View {
    val inflater = LayoutInflater.from(parent.context)
    return inflater.inflate(layoutRes, parent, false)
}

fun RecyclerView.ViewHolder.getContext(): Context {
    return itemView.context
}