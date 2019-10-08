package com.plugow.shoppingapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.model.SearchItem
import com.plugow.shoppingapp.db.model.ShoppingList
import kotlinx.android.synthetic.main.shopping_list_item.*
import org.jetbrains.anko.backgroundColorResource


class SearchAdapter : BaseAdapter<SearchItem>() {
    override fun setData(items: List<SearchItem>) {
        val oldItems = values
        values = items as ArrayList<SearchItem>
        autoNotify(oldItems, values) { oldItem, newItem ->
            oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SearchItem> {
        return SearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<SearchItem>, position: Int) {
        val shoppingList = values[position]
        holder.bind(shoppingList)
        holder.containerView.setOnClickListener {
            shoppingList.isChosen = !shoppingList.isChosen
            if (shoppingList.isChosen){
                onRecyclerListener.onClick(SearchClickType.ADD, position)
            } else {
                onRecyclerListener.onClick(SearchClickType.REMOVE, position)
            }
            notifyItemChanged(position)
        }
    }

    class SearchViewHolder(containerView: View) : BaseViewHolder<SearchItem>(containerView) {
        override fun bind(item: SearchItem) {
            title.text = item.name
            if (item.isChosen){
                containerView.backgroundColorResource = R.color.lightAccent
            } else {
                containerView.backgroundColorResource = R.color.white
            }
        }
    }

}