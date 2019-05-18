package com.plugow.shoppingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.db.model.Product
import com.plugow.shoppingapp.di.util.Event
import com.plugow.shoppingapp.event.ShoppingListEvent
import com.plugow.shoppingapp.trait.RefreshableList
import com.plugow.shoppingapp.ui.adapter.ClickType
import com.plugow.shoppingapp.ui.adapter.ProductClickType
import com.plugow.shoppingapp.ui.adapter.RecyclerClickType
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ShoppingListDetailViewModel @Inject constructor(): ViewModel(), RefreshableList<Product> {
    val mEvent:MutableLiveData<Event<ShoppingListEvent>> = MutableLiveData()
    val event : LiveData<Event<ShoppingListEvent>>
        get() = mEvent

    override fun loadItems() {
        val list = arrayListOf(Product(name = "pierwszy"), Product(name = "drugi"))
        items.value = list
    }

    override var items: MutableLiveData<List<Product>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)

    private val disposables= CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun addProduct() {

    }

    fun onRecyclerClick(type:ClickType, pos:Int){
        when(type){
            ProductClickType.ADD -> {}
            ProductClickType.SUBSTRACT -> {}
            ProductClickType.CHECK -> {}
        }
    }


}