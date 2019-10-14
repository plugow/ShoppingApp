package com.plugow.shoppingapp.ui.adapter


interface BindableAdapter<in T> {

    fun setData(items: List<T>)
    fun setListener(listener: (type:ClickType, pos:Int) -> Unit)

}