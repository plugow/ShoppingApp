package com.plugow.shoppingapp.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.ui.adapter.BindableAdapter
import com.plugow.shoppingapp.ui.adapter.OnRecyclerListener


@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun <T> setRecyclerData(recyclerView: RecyclerView, items: List<T>?) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        items?.let {
            (recyclerView.adapter as BindableAdapter<T>).setData(it)
        }
    }
}


@BindingAdapter("onRecyclerClick")
fun setRecyclerListener(recyclerView: RecyclerView, onRecyclerListener: OnRecyclerListener){
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<*>).setListener(onRecyclerListener)
    }
}

@BindingAdapter("isSelected")
fun setBackground(view: View, isSelected:Boolean) {
    if(isSelected) view.setBackgroundResource(R.color.lightAccent)
    else view.setBackgroundResource(R.color.white)
}

