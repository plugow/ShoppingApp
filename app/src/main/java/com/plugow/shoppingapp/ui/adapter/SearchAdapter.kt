package com.plugow.shoppingapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugow.shoppingapp.R
import kotlinx.android.synthetic.main.shopping_list_item.*


class SearchAdapter : BaseAdapter<SearchItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SearchItem> {
        return SearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<SearchItem>, position: Int) {
        val shoppingList = values[position]
        holder.bind(shoppingList)
        holder.containerView.setOnClickListener {
            onRecyclerListener.onClick(SearchClickType.MAIN, position)
        }
    }

    class SearchViewHolder(containerView: View) : BaseViewHolder<SearchItem>(containerView) {
        override fun bind(item: SearchItem) {
            title.text = item.name
        }
    }

}