package com.plugow.shoppingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.db.AppRepo
import com.plugow.shoppingapp.db.model.Product
import com.plugow.shoppingapp.util.Event
import com.plugow.shoppingapp.event.BusEvent
import com.plugow.shoppingapp.event.RxBus
import com.plugow.shoppingapp.event.ShoppingListEvent
import com.plugow.shoppingapp.trait.RefreshableList
import com.plugow.shoppingapp.ui.adapter.*
import com.raizlabs.android.dbflow.kotlinextensions.delete
import com.raizlabs.android.dbflow.kotlinextensions.update
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ShoppingListDetailViewModel @Inject constructor(private val repo: AppRepo, private val rxBus: RxBus): ViewModel(), RefreshableList<Product> {
    override var items: MutableLiveData<List<Product>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    private val disposables= CompositeDisposable()
    private var shoppingListId = 0
    private val mEvent:MutableLiveData<Event<ShoppingListEvent>> = MutableLiveData()
    val event : LiveData<Event<ShoppingListEvent>>
        get() = mEvent

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    override fun loadItems() {
        repo.getProductById(shoppingListId)
            .subscribeBy(
                onSuccess = {
                    items.value = it
                }
            ).addTo(disposables)
    }

    fun initValues(id:Int){
        rxBus.getEventObservable().subscribeBy {
            if (it == BusEvent.REFRESH_PRODUCTS){
                loadItems()
            }
        }.addTo(disposables)
        shoppingListId = id
        loadItems()
    }

    override fun onRecyclerClick(type:ClickType, pos:Int){
        when(type){
            ProductClickType.ADD -> items.value?.get(pos)?.update()
            ProductClickType.SUBSTRACT -> {
                if (items.value?.get(pos)?.amount!! == 1){
                    items.value?.get(pos)?.delete()
                } else {
                    items.value?.get(pos)?.update()
                }
            }
            ProductClickType.CHECK -> items.value?.get(pos)?.update()
        }
    }

}