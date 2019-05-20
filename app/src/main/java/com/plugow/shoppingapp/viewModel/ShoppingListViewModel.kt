package com.plugow.shoppingapp.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.AppRepo
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.util.Event
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
import javax.inject.Inject

class ShoppingListViewModel @Inject constructor(private val repo: AppRepo, private val ctx:Context): ViewModel(), RefreshableList<ShoppingList> {
    override var items: MutableLiveData<List<ShoppingList>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    var currentItemId = 0
    private val disposables= CompositeDisposable()
    private val mEvent:MutableLiveData<Event<ShoppingListEvent>> = MutableLiveData()
    val event : LiveData<Event<ShoppingListEvent>>
        get() = mEvent
    var isAscending = false


    override fun loadItems() {
        repo.getShoppingList()
            .subscribeBy(
                onSuccess = {
                    items.value = it
                }
            ).addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun addList(text: String) {
        val name = if (text=="") ctx.getString(R.string.new_list) else text
        val newList = ShoppingList(name = name)
        newList.save()
        val temp = items.value?.toMutableList()
        temp?.add(0,newList)
        items.value = temp
    }

    override fun onRecyclerClick(type: ClickType, pos:Int){
        when(type){
            RecyclerClickType.MAIN -> {
                currentItemId = items.value?.get(pos)?.id ?: 0
                mEvent.value = Event(ShoppingListEvent.OnItemClick)
            }
            RecyclerClickType.REMOVE -> {
                items.value?.get(pos)?.delete()
            }
            RecyclerClickType.ARCHIVE -> {
                items.value?.get(pos)?.update()
            }
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