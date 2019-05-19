package com.plugow.shoppingapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.model.Product
import kotlinx.android.synthetic.main.product_list_item.*


class ShoppingListDetailAdapter : BaseAdapter<Product>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Product> {
        return ShoppingListDetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false))
    }


    override fun onBindViewHolder(holder: BaseViewHolder<Product>, position: Int) {
        val product = values[position]
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
                values.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, values.size)
            }
        }
        holder.doneCheckBox.setOnCheckedChangeListener { _, b ->
            product.isDone = b
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

        }
    }

}