package com.plugow.shoppingapp.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseHolder<in T>(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    abstract fun bind(item: T, listener: ((ClickType, Int) -> Unit)? = null)
}