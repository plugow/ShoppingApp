package com.plugow.shoppingapp.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.db.AppRepo
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.event.BusEvent
import com.plugow.shoppingapp.event.RxBus
import com.plugow.shoppingapp.trait.RefreshableList
import com.plugow.shoppingapp.ui.adapter.ArchiveClickType
import com.plugow.shoppingapp.ui.adapter.ClickType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ArchivedListViewModel @Inject constructor(private val repo: AppRepo, private val rxBus: RxBus): ViewModel(), RefreshableList<ShoppingList>{
    override var items: MutableLiveData<List<ShoppingList>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    var isAscending = false
    private val disposables= CompositeDisposable()


    override fun onRecyclerClick(type: ClickType, pos: Int) {
        when(type){
            ArchiveClickType.REMOVE -> {
                items.value?.get(pos)?.delete()
            }
            ArchiveClickType.RESTORE -> {
                items.value?.get(pos)?.update()
                rxBus.emitEvent(BusEvent.RefreshShoppingLists)
            }
        }
    }

    override fun loadItems() {
        repo.getArchivedList()
            .subscribeBy(
                onSuccess = {
                    items.value = it
                    isLoadingRefresh.value = false
                }
            ).addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    override fun initValues(id: Int) {
        super.initValues(id)
        rxBus.getEventObservable()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe {
                when (it) {
                    is BusEvent.RefreshArchivedLists -> loadItems()
                }
            }.addTo(disposables)
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