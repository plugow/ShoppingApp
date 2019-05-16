package com.plugow.shoppingapp.ui.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.databinding.FragmentShoppingListBinding
import com.plugow.shoppingapp.ui.adapter.ShoppingListAdapter
import com.plugow.shoppingapp.viewModel.ArchivedListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ShoppingListFragment : DaggerFragment() {
    @Inject lateinit var viewModelFactory:ViewModelProvider.Factory
    private lateinit var mViewModel: ArchivedListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(ArchivedListViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mAdapter= ShoppingListAdapter()
        val binding = DataBindingUtil.inflate<FragmentShoppingListBinding>(inflater, R.layout.fragment_shopping_list, container, false ).apply {
            lifecycleOwner = this@ShoppingListFragment
            viewModel = mViewModel
            list.adapter = mAdapter
            list.layoutManager = LinearLayoutManager(context)
        }

        val layout = binding.episodesLayout
        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
                ShoppingListFragment()
    }


}
