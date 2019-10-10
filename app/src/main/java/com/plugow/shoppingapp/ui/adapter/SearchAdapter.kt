package com.plugow.shoppingapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.model.SearchItem
import kotlinx.android.synthetic.main.shopping_list_item.*
import org.jetbrains.anko.backgroundColorResource


class SearchAdapter : RecyclerView.Adapter<BaseViewHolder<SearchItem>>(), BindableAdapter<SearchItem> {
    private var items: List<SearchItem> = emptyList()

    override fun getItemCount(): Int = items.size

    override lateinit var onRecyclerListener: OnRecyclerListener

    override fun setData(items: List<SearchItem>) {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SearchItem> {
        return SearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<SearchItem>, position: Int) {
        val shoppingList = items[position]
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