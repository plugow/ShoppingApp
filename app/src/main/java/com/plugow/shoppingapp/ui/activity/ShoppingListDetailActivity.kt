package com.plugow.shoppingapp.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.databinding.ActivityShoppingListDetailBinding
import com.plugow.shoppingapp.ui.adapter.ShoppingListDetailAdapter
import com.plugow.shoppingapp.ui.dialog.SearchDialogFragment
import com.plugow.shoppingapp.viewModel.ShoppingListDetailViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ShoppingListDetailActivity : DaggerAppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: ShoppingListDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, viewModelFactory)[ShoppingListDetailViewModel::class.java]
        actionBar?.setHomeButtonEnabled(true)
        val mAdapter = ShoppingListDetailAdapter()
        val id = intent.getIntExtra("id", 0)
        val binding = DataBindingUtil.setContentView<ActivityShoppingListDetailBinding>(this, R.layout.activity_shopping_list_detail).apply {
            lifecycleOwner = this@ShoppingListDetailActivity
            viewModel = mViewModel
            list.adapter = mAdapter
            list.layoutManager = LinearLayoutManager(this@ShoppingListDetailActivity)
            addProductButton.setOnClickListener {
                val searchDialog = SearchDialogFragment.newInstance(id)
                searchDialog.show(supportFragmentManager,"search dialog")
            }
        }
        mViewModel.initValues(id)
    }
}