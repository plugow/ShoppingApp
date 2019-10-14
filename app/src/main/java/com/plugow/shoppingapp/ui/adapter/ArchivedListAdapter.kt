package com.plugow.shoppingapp.ui.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.util.BaseAdapter
import kotlinx.android.synthetic.main.archived_list_item.*
import kotlin.properties.Delegates


class ArchivedListAdapter : BaseAdapter<ShoppingList>(), AutoUpdatableAdapter,
    BindableAdapter<ShoppingList> {
    private lateinit var onRecyclerListener: (ClickType, Int) -> Unit
    private var items: List<ShoppingList> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.id == n.id }
    }

    override fun setData(items: List<ShoppingList>) {
        this.items = items
    }

    override fun setListener(listener: (type: ClickType, pos: Int) -> Unit) {
        this.onRecyclerListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<ShoppingList> {
        return ArchivedListHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.archived_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseHolder<ShoppingList>, position: Int) {
        val shoppingList = items[position]
        holder.bind(shoppingList, onRecyclerListener)
    }

    override fun getItemCount(): Int = items.size

    class ArchivedListHolder(containerView: View) :
        BaseHolder<ShoppingList>(containerView) {
        override fun bind(item: ShoppingList, listener: ((ClickType, Int) -> Unit)?) {
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
            contextMenu.setOnClickListener {
                PopupMenu(containerView.context, contextMenu, Gravity.END).apply {
                    menuInflater.inflate(R.menu.archived_list_menu, menu)
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.nav_restore -> {
                                listener?.invoke(ArchiveClickType.RESTORE, adapterPosition)
                            }
                            R.id.nav_remove -> {
                                listener?.invoke(ArchiveClickType.REMOVE, adapterPosition)
                            }
                        }
                        true
                    }
                    show()
                }
            }
        }
    }

}