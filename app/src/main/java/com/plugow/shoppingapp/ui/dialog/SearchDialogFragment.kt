package com.plugow.shoppingapp.ui.dialog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.databinding.FragmentSearchDialogBinding
import com.plugow.shoppingapp.ui.adapter.SearchAdapter
import com.plugow.shoppingapp.viewModel.ShoppingListDetailViewModel
import dagger.android.support.DaggerAppCompatDialogFragment
import org.jetbrains.anko.backgroundColor
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration



class SearchDialogFragment : DaggerAppCompatDialogFragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mViewModel:ShoppingListDetailViewModel
    companion object {
        fun newInstance() : SearchDialogFragment = SearchDialogFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mAdapter = SearchAdapter()
        val binding =DataBindingUtil.inflate<FragmentSearchDialogBinding>(layoutInflater,  R.layout.fragment_search_dialog, container, true).apply {
            lifecycleOwner = this@SearchDialogFragment
            viewModel=mViewModel
            list.adapter = mAdapter
            list.layoutManager = LinearLayoutManager(context)
            list.addItemDecoration(
                DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
            )
            readyButton.setOnClickListener {
                dismiss()
            }
        }
        binding.viewModel=mViewModel
        mViewModel.loadSearchItems()


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(ShoppingListDetailViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onResume() {
        super.onResume()
        isCancelable=false
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, 500.dpToPxl())
        dialog?.window?.decorView?.backgroundColor = Color.TRANSPARENT
    }

    private fun Int.dpToPxl():Int  {
        val scale = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
}