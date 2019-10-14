package com.plugow.shoppingapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.model.Product
import com.plugow.shoppingapp.util.BaseAdapter
import kotlinx.android.synthetic.main.product_list_item.*
import org.jetbrains.anko.backgroundColorResource
import kotlin.properties.Delegates


class ShoppingListDetailAdapter : BaseAdapter<Product>(), AutoUpdatableAdapter, BindableAdapter<Product> {

    private lateinit var onRecyclerListener: (ClickType, Int) -> Unit
    private var items: List<Product> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.id == n.id }
    }

    override fun setData(items: List<Product>) {
        this.items = items
    }

    override fun setListener(listener: (type: ClickType, pos: Int) -> Unit) {
        this.onRecyclerListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Product> {
        return ShoppingListDetailHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false))
    }


    override fun onBindViewHolder(holder: BaseHolder<Product>, position: Int) {
        val product = items[position]
        holder.bind(product)
        holder.plusButton.setOnClickListener {
            product.amount += 1
            notifyItemChanged(position)
            onRecyclerListener(ProductClickType.ADD, position)
        }
        holder.minusButton.setOnClickListener {
            onRecyclerListener(ProductClickType.SUBSTRACT, position)
            if (product.amount > 1){
                product.amount -= 1
                notifyItemChanged(position)
            } else {
                val items = this.items.toMutableList()
                items.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, items.size)
                this.items = items
            }
        }
        holder.doneCheckBox.setOnClickListener {
            product.isDone = !product.isDone
            notifyItemChanged(position)
            onRecyclerListener(ProductClickType.CHECK, position)
        }
    }

    override fun getItemCount(): Int = items.size


    class ShoppingListDetailHolder(containerView: View) : BaseHolder<Product>(containerView) {

        override fun bind(item: Product, listener: ((ClickType, Int) -> Unit)?) {
            title.text = item.name
            if (item.amount!=1){
                amount.text = item.amount.toString()
            } else {
                amount.text = ""
            }
            doneCheckBox.isChecked = item.isDone
            if (item.isDone){
                containerView.backgroundColorResource = R.color.detailGrey
            } else {
                containerView.backgroundColorResource = R.color.white
            }

        }
    }

}