package com.plugow.shoppingapp.ui.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.PopupMenu
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
        holder.containerView.setOnClickListener {
            onRecyclerListener.onClick(RecyclerClickType.MAIN, position)
        }
        holder.contextMenu.setOnClickListener {
            val popup = PopupMenu(holder.containerView.context, holder.contextMenu, Gravity.END)
            popup.menuInflater.inflate(R.menu.shopping_list_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.nav_archive -> {onRecyclerListener.onClick(RecyclerClickType.ARCHIVE, position)}
                    R.id.nav_remove -> onRecyclerListener.onClick(RecyclerClickType.REMOVE, position)
                }
                true
            }
            popup.show()
        }
    }

    class ShoppingListViewHolder(containerView: View) : BaseViewHolder<ShoppingList>(containerView) {
        lateinit var menu:ImageButton
        override fun bind(item: ShoppingList) {
            title.text = item.name
            menu = contextMenu
        }
    }

}