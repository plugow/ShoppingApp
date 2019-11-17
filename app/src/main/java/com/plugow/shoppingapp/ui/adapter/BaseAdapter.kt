package com.plugow.shoppingapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseHolder<T>>() {
    protected abstract var items: List<T>
    fun setData(items: List<T>) {
        this.items = items
    }
}
