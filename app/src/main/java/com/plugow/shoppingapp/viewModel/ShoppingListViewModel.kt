package com.plugow.shoppingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.db.AppRepo
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.di.util.Event
import com.plugow.shoppingapp.event.ShoppingListEvent
import com.plugow.shoppingapp.trait.RefreshableList
import com.plugow.shoppingapp.ui.adapter.ClickType
import com.plugow.shoppingapp.ui.adapter.RecyclerClickType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class ShoppingListViewModel @Inject constructor(val repo: AppRepo): ViewModel(), RefreshableList<ShoppingList> {
    override var items: MutableLiveData<List<ShoppingList>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    var currentItemId = 0
    private val disposables= CompositeDisposable()
    private val mEvent:MutableLiveData<Event<ShoppingListEvent>> = MutableLiveData()
    val event : LiveData<Event<ShoppingListEvent>>
        get() = mEvent
    var isAscending = false


    override fun loadItems() {
        val d = Calendar.getInstance()
        d.set(2018, 7, 20)
        val list = arrayListOf(ShoppingList(id = 1, name = "test", createdAt = Date()), ShoppingList(id = 2, name = "test 2", createdAt = d.time))
        items.value = list
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun addList() {

    }

    override fun onRecyclerClick(type: ClickType, pos:Int){
        when(type){
            RecyclerClickType.MAIN -> {
                currentItemId = items.value?.get(pos)?.id ?: 0
                mEvent.value = Event(ShoppingListEvent.OnItemClick)
            }
            RecyclerClickType.REMOVE -> {}
            RecyclerClickType.ARCHIVE -> {}
        }
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