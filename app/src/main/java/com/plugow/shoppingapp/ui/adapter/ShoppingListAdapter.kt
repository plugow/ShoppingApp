package com.plugow.shoppingapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.model.ShoppingList
import kotlinx.android.synthetic.main.shopping_list_item.*


class ShoppingListAdapter : BaseAdapter<ShoppingList>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ShoppingList> {
        return ShoppingListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.shopping_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ShoppingList>, position: Int) {
        val shoppingList = values[position]
        holder.bind(shoppingList)
    }

    class ShoppingListViewHolder(containerView: View) : BaseViewHolder<ShoppingList>(containerView) {
        override fun bind(item: ShoppingList) {
            title.text = item.name
        }
    }

}