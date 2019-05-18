package com.plugow.shoppingapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.model.Product


class ShoppingListDetailAdapter : BaseAdapter<Product>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Product> {
        return ShoppingListDetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false))
    }


    override fun onBindViewHolder(holder: BaseViewHolder<Product>, position: Int) {
        val product = values[position]
        holder.bind(product)
    }



    class ShoppingListDetailViewHolder(containerView: View) : BaseViewHolder<Product>(containerView) {
        override fun bind(item: Product) {
        }
    }

}