package com.plugow.shoppingapp.ui.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.model.ShoppingList
import kotlinx.android.synthetic.main.archived_list_item.*
import kotlin.properties.Delegates


class ArchivedListAdapter : RecyclerView.Adapter<BaseViewHolder<ShoppingList>>(), AutoUpdatableAdapter, BindableAdapter<ShoppingList> {
    override lateinit var onRecyclerListener: OnRecyclerListener

    private var items: List<ShoppingList> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.id == n.id }
    }

    override fun setData(items: List<ShoppingList>) {
        this.items = items
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ShoppingList> {
        return ArchivedListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.archived_list_item,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: BaseViewHolder<ShoppingList>, position: Int) {
        val shoppingList = items[position]
        holder.bind(shoppingList)
        holder.contextMenu.setOnClickListener {
            val popup = PopupMenu(holder.containerView.context, holder.contextMenu, Gravity.END)
            popup.menuInflater.inflate(R.menu.archived_list_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                val items = this.items.toMutableList()
                when (it.itemId) {
                    R.id.nav_restore -> {
                        shoppingList.isArchived = false
                        onRecyclerListener.onClick(ArchiveClickType.RESTORE, position)
                    }
                    R.id.nav_remove -> {
                        onRecyclerListener.onClick(ArchiveClickType.REMOVE, position)
                    }
                }
                this.items = items
                true
            }
            popup.show()
        }
    }


    class ArchivedListViewHolder(containerView: View) :
        BaseViewHolder<ShoppingList>(containerView) {
        override fun bind(item: ShoppingList) {
            title.text = title.context.getString(R.string.tile_name, item.name)
            if (item.productsAmount > 0) {
                shoppingResult.text = shoppingResult.context.getString(
                    R.string.shopping_result,
                    item.doneAmount,
                    item.productsAmount
                )
                shoppingProgress.progress =
                    ((item.doneAmount.toFloat() / item.productsAmount.toFloat()) * 100).toInt()
            } else {
                shoppingResult.text = ""
                shoppingProgress.progress = 0
            }
        }
    }

}