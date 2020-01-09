package com.plugow.shoppingapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.model.SearchItem
import kotlinx.android.synthetic.main.shopping_list_item.*
import org.jetbrains.anko.backgroundColorResource
import kotlin.properties.Delegates

class SearchAdapter : BaseAdapter<SearchItem>() {

    override var items: List<SearchItem> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<SearchItem> {
        return SearchHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.search_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseHolder<SearchItem>, position: Int) {
        val shoppingList = items[position]
        holder.bind(shoppingList, onRecyclerListener)
        holder.containerView.setOnClickListener {
            shoppingList.isChosen = !shoppingList.isChosen
            if (shoppingList.isChosen) {
                onRecyclerListener?.invoke(SearchClickType.ADD, position)
            } else {
                onRecyclerListener?.invoke(SearchClickType.REMOVE, position)
            }
            notifyItemChanged(position)
        }
    }

    class SearchHolder(containerView: View) : BaseHolder<SearchItem>(containerView) {
        override fun bind(item: SearchItem, listener: ((ClickType, Int) -> Unit)?) {
            title.text = item.name
            if (item.isChosen) {
                containerView.backgroundColorResource = R.color.lightAccent
            } else {
                containerView.backgroundColorResource = R.color.white
            }
        }
    }
}
