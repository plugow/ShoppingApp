package com.plugow.shoppingapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.databinding.FragmentArchivedListBinding
import com.plugow.shoppingapp.ui.adapter.ArchivedListAdapter
import com.plugow.shoppingapp.viewModel.ArchivedListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ArchivedListFragment : DaggerFragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: ArchivedListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(ArchivedListViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mAdapter= ArchivedListAdapter()
        val binding = DataBindingUtil.inflate<FragmentArchivedListBinding>(inflater, R.layout.fragment_archived_list, container, false ).apply {
            lifecycleOwner = this@ArchivedListFragment
            viewModel = mViewModel
            list.adapter = mAdapter
            list.layoutManager = LinearLayoutManager(context)
        }

        val layout = binding.charactersLayout
        return binding.root
    }



    companion object {
        @JvmStatic
        fun newInstance() =
                ArchivedListFragment()
    }

}
