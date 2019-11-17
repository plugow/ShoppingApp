package com.plugow.shoppingapp.ui.adapter

interface ClickableAdapter<in T> {

    fun setListener(listener: (type: ClickType, pos: Int) -> Unit)
}
