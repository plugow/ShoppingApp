package com.plugow.shoppingapp.ui.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.databinding.FragmentShoppingListBinding
import com.plugow.shoppingapp.event.ShoppingListEvent
import com.plugow.shoppingapp.ui.adapter.ShoppingListAdapter
import com.plugow.shoppingapp.ui.dialog.NewListDialog
import com.plugow.shoppingapp.viewModel.ShoppingListViewModel
import dagger.android.support.DaggerFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.contentView
import javax.inject.Inject

class ShoppingListFragment : DaggerFragment() {
    @Inject lateinit var viewModelFactory:ViewModelProvider.Factory
    private lateinit var mViewModel: ShoppingListViewModel
    var newListDialog:NewListDialog?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(ShoppingListViewModel::class.java)
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
            addListButton.setOnClickListener {
                addNewList()
            }
        }
        mViewModel.loadItems()
        mViewModel.event.observe(this, Observer {
            when(it.getContentIfNotHandled()){
                ShoppingListEvent.OnItemClick -> {}
                null -> {}
            }
        })
        return binding.root
    }

    fun addNewList(){
        newListDialog= activity?.contentView?.let {
            NewListDialog(AnkoContext.create(activity!!, it))
        }
        newListDialog?.okButton?.setOnClickListener {
            mViewModel.addList()
            newListDialog?.pDialog?.dismiss()
        }
        newListDialog?.cancelButton?.setOnClickListener {
            newListDialog?.pDialog?.dismiss()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
                ShoppingListFragment()
    }


}
