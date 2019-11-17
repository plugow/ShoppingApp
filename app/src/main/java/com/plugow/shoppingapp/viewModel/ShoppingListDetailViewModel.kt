package com.plugow.shoppingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.db.model.Product
import com.plugow.shoppingapp.domain.DeleteProductUseCase
import com.plugow.shoppingapp.domain.GetProductsUseCase
import com.plugow.shoppingapp.domain.UpdateProductUseCase
import com.plugow.shoppingapp.event.ShoppingListEvent
import com.plugow.shoppingapp.trait.RefreshableList
import com.plugow.shoppingapp.ui.adapter.ClickType
import com.plugow.shoppingapp.ui.adapter.ProductClickType
import com.plugow.shoppingapp.util.Event
import javax.inject.Inject

class ShoppingListDetailViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) : ViewModel(), RefreshableList<Product> {
    override var items: MutableLiveData<List<Product>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    private var shoppingListId = 0
    private val mEvent: MutableLiveData<Event<ShoppingListEvent>> = MutableLiveData()
    val event: LiveData<Event<ShoppingListEvent>>
        get() = mEvent

    var onClickListener: MutableLiveData<(ClickType, Int) -> Unit> = MutableLiveData { type, pos ->
        onRecyclerClick(type, pos)
    }

    override fun onCleared() {
        super.onCleared()
        getProductsUseCase.dispose()
        deleteProductUseCase.dispose()
        updateProductUseCase.dispose()
    }

    override fun loadItems() {
        getProductsUseCase.execute(params = shoppingListId, onNext = {
            items.value = it
            isLoadingRefresh.value = false
        })
    }

    override fun initValues(id: Int) {
        shoppingListId = id
        super.initValues(id)
    }

    override fun onRecyclerClick(type: ClickType, pos: Int) {
        when (type) {
            ProductClickType.ADD -> updateProductUseCase.execute(params = items.value?.get(pos))
            ProductClickType.SUBSTRACT -> {
                if (items.value?.get(pos)?.amount!! == 1) {
                    deleteProductUseCase.execute(params = items.value?.get(pos))
                } else {
                    updateProductUseCase.execute(params = items.value?.get(pos))
                }
            }
            ProductClickType.CHECK -> updateProductUseCase.execute(params = items.value?.get(pos))
        }
    }
}
