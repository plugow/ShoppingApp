package com.plugow.shoppingapp.ui.adapter

interface BindableAdapter<T> {

    var onRecyclerListener: OnRecyclerListener

    fun setData(items: List<T>)

    fun setListener(listener: OnRecyclerListener) {
        onRecyclerListener = listener
    }
}