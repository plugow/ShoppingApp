package com.plugow.shoppingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.di.util.Event
import com.plugow.shoppingapp.event.ShoppingListEvent
import com.plugow.shoppingapp.trait.RefreshableList
import com.plugow.shoppingapp.ui.adapter.ClickType
import com.plugow.shoppingapp.ui.adapter.RecyclerClickType
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ShoppingListViewModel @Inject constructor(): ViewModel(), RefreshableList<ShoppingList> {
    var currentItemId = 0
    val mEvent:MutableLiveData<Event<ShoppingListEvent>> = MutableLiveData()
    val event : LiveData<Event<ShoppingListEvent>>
        get() = mEvent

    override fun loadItems() {
        val list = arrayListOf(ShoppingList(name = "test"), ShoppingList(name = "test 2"))
        items.value = list
    }

    override var items: MutableLiveData<List<ShoppingList>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)

    private val disposables= CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun addList() {

    }

    fun onRecyclerClick(type: ClickType, pos:Int){
        when(type){
            RecyclerClickType.MAIN -> {
                currentItemId = items.value?.get(pos)?.id ?: 0
                mEvent.value = Event(ShoppingListEvent.OnItemClick)
            }
            RecyclerClickType.REMOVE -> {}
            RecyclerClickType.ARCHIVE -> {}
        }
    }


}