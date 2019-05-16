package com.plugow.shoppingapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {
    protected var values: ArrayList<T> = arrayListOf()

    fun setData(items: List<T>) {
        values =items as ArrayList<T>
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return values.size
    }

}