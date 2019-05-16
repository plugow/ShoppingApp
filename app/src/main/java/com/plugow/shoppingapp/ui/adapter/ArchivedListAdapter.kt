package com.plugow.shoppingapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.model.ShoppingList


class ArchivedListAdapter : BaseAdapter<ShoppingList>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ShoppingList> {
        return ArchivedListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.archived_list_item, parent, false))
    }


    override fun onBindViewHolder(holder: BaseViewHolder<ShoppingList>, position: Int) {
        val shoppingList = values[position]
        holder.bind(shoppingList)
    }



    class ArchivedListViewHolder(containerView: View) : BaseViewHolder<ShoppingList>(containerView) {
        override fun bind(item: ShoppingList) {
        }
    }

}