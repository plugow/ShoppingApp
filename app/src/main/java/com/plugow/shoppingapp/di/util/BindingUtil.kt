package com.plugow.shoppingapp.di.util

import android.view.View
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.ui.adapter.BaseAdapter
import com.plugow.shoppingapp.ui.adapter.OnRecyclerListener


@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun <T> setRecyclerData(recyclerView: RecyclerView, items: List<T>?) {
    if (recyclerView.adapter is BaseAdapter<*>) {
        items?.let {
            (recyclerView.adapter as BaseAdapter<T>).setData(it)
        }
    }
}


@BindingAdapter("onRecyclerClick")
fun setRecyclerListener(recyclerView: RecyclerView, onRecyclerListener: OnRecyclerListener){
    if (recyclerView.adapter is BaseAdapter<*>) {
        (recyclerView.adapter as BaseAdapter<*>).setListener(onRecyclerListener)
    }
}

@BindingAdapter("isSelected")
fun setBackground(view: View, isSelected:Boolean) {
    if(isSelected) view.setBackgroundResource(R.color.lightAccent)
    else view.setBackgroundResource(R.color.white)
}

