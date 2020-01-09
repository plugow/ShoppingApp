package com.plugow.shoppingapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseHolder<T>>(){
    protected abstract var items: List<T>
    protected var onRecyclerListener: ((ClickType, Int) -> Unit)? = null

    fun setData(items: List<T>) {
        this.items = items
    }

    fun setListener(listener: (type: ClickType, pos: Int) -> Unit) {
        this.onRecyclerListener = listener
    }

    override fun getItemCount(): Int = items.size
}