package com.plugow.shoppingapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.model.Product
import kotlinx.android.synthetic.main.product_list_item.*
import org.jetbrains.anko.backgroundColorResource
import kotlin.properties.Delegates


class ShoppingListDetailAdapter : RecyclerView.Adapter<BaseViewHolder<Product>>(), AutoUpdatableAdapter, BindableAdapter<Product> {
    override fun getItemCount(): Int = items.size

    override lateinit var onRecyclerListener: OnRecyclerListener

    private var items: List<Product> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.id == n.id }
    }
    override fun setData(items: List<Product>) {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Product> {
        return ShoppingListDetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false))
    }


    override fun onBindViewHolder(holder: BaseViewHolder<Product>, position: Int) {
        val product = items[position]
        holder.bind(product)
        holder.plusButton.setOnClickListener {
            product.amount += 1
            notifyItemChanged(position)
            onRecyclerListener.onClick(ProductClickType.ADD, position)
        }
        holder.minusButton.setOnClickListener {
            onRecyclerListener.onClick(ProductClickType.SUBSTRACT, position)
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
            onRecyclerListener.onClick(ProductClickType.CHECK, position)
        }
    }



    class ShoppingListDetailViewHolder(containerView: View) : BaseViewHolder<Product>(containerView) {

        override fun bind(item: Product) {
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