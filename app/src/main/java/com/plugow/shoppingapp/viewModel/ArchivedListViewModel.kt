package com.plugow.shoppingapp.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.trait.RefreshableList
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ArchivedListViewModel @Inject constructor(): ViewModel(), RefreshableList<ShoppingList>{
    override fun loadItems() {
    }

    override var items: MutableLiveData<List<ShoppingList>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)

    private val disposables= CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}