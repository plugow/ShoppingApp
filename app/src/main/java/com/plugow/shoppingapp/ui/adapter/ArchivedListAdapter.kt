package com.plugow.shoppingapp.ui.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.model.ShoppingList
import kotlinx.android.synthetic.main.archived_list_item.*


class ArchivedListAdapter : BaseAdapter<ShoppingList>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ShoppingList> {
        return ArchivedListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.archived_list_item, parent, false))
    }


    override fun onBindViewHolder(holder: BaseViewHolder<ShoppingList>, position: Int) {
        val shoppingList = values[position]
        holder.bind(shoppingList)
        holder.contextMenu.setOnClickListener {
            val popup = PopupMenu(holder.containerView.context, holder.contextMenu, Gravity.END)
            popup.menuInflater.inflate(R.menu.archived_list_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.nav_restore -> {
                        shoppingList.isArchived=false
                        onRecyclerListener.onClick(ArchiveClickType.RESTORE, position)
                        values.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, values.size)
                    }
                    R.id.nav_remove -> {
                        onRecyclerListener.onClick(ArchiveClickType.REMOVE, position)
                        values.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, values.size)
                    }
                }
                true
            }
            popup.show()
        }
    }



    class ArchivedListViewHolder(containerView: View) : BaseViewHolder<ShoppingList>(containerView) {
        override fun bind(item: ShoppingList) {
            title.text = item.name
        }
    }

}