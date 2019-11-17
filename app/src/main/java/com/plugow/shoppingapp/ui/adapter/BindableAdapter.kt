package com.plugow.shoppingapp.ui.adapter


interface BindableAdapter {
    fun setListener(listener: (type:ClickType, pos:Int) -> Unit)
}


class BindableAdapterImpl : BindableAdapter {
    lateinit var onRecyclerClickListener: (type: ClickType, pos: Int) -> Unit

    override fun setListener(listener: (type: ClickType, pos: Int) -> Unit) {
        onRecyclerClickListener = listener

    }
}