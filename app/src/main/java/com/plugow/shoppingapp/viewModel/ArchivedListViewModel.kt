package com.plugow.shoppingapp.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.db.AppRepo
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.trait.RefreshableList
import com.plugow.shoppingapp.ui.adapter.ClickType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArchivedListViewModel @Inject constructor(val repo: AppRepo): ViewModel(), RefreshableList<ShoppingList>{
    var isAscending = false
    override fun onRecyclerClick(type: ClickType, pos: Int) {

    }

    override fun loadItems() {
        repo.getArchivedList()
            .subscribeBy(
                onSuccess = {
                    items.value = it
                }
            ).addTo(disposables)
    }

    override var items: MutableLiveData<List<ShoppingList>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)

    private val disposables= CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun sort() {
        items.value?.let {
            it.toObservable()
                .toSortedList { a, b ->
                    isAscending = ! isAscending
                    if (isAscending) a.createdAt.compareTo(b.createdAt)
                    else b.createdAt.compareTo(a.createdAt)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {sortedList ->
                        items.value = sortedList
                    }
                ).addTo(disposables)
        }
    }

}