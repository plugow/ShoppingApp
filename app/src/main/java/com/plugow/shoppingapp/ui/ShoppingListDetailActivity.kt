package com.plugow.shoppingapp.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.databinding.ActivityShoppingListDetailBinding
import com.plugow.shoppingapp.ui.adapter.ShoppingListDetailAdapter
import com.plugow.shoppingapp.viewModel.ShoppingListDetailViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ShoppingListDetailActivity : DaggerAppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: ShoppingListDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, viewModelFactory)[ShoppingListDetailViewModel::class.java]
        val mAdapter = ShoppingListDetailAdapter()
        val binding = DataBindingUtil.setContentView<ActivityShoppingListDetailBinding>(this, R.layout.activity_shopping_list_detail).apply {
            lifecycleOwner = this@ShoppingListDetailActivity
            viewModel = mViewModel
            list.adapter = mAdapter
            list.layoutManager = LinearLayoutManager(this@ShoppingListDetailActivity)
        }
        mViewModel.loadItems()
    }
}
