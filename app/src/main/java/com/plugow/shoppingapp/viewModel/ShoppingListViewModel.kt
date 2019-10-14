package com.plugow.shoppingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.domain.*
import com.plugow.shoppingapp.util.Event
import com.plugow.shoppingapp.event.ShoppingListEvent
import com.plugow.shoppingapp.trait.RefreshableList
import com.plugow.shoppingapp.ui.adapter.ClickType
import com.plugow.shoppingapp.ui.adapter.RecyclerClickType
import javax.inject.Inject

class ShoppingListViewModel @Inject constructor(
    private val deleteListUseCase: DeleteListUseCase,
    private val updateListUseCase: UpdateListUseCase,
    private val sortListUseCase: SortListUseCase,
    private val getShoppingListUseCase: GetShoppingListUseCase,
    private val addListUseCase: AddListUseCase
) : ViewModel(), RefreshableList<ShoppingList> {

    override var items: MutableLiveData<List<ShoppingList>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    lateinit var currentItem: ShoppingList
    private val mEvent: MutableLiveData<Event<ShoppingListEvent>> = MutableLiveData()
    val event: LiveData<Event<ShoppingListEvent>>
        get() = mEvent
    var isAscending = false

    var onClickListener: MutableLiveData<(ClickType, Int) -> Unit> = MutableLiveData { type, pos ->
        onRecyclerClick(type, pos)
    }


    override fun loadItems() {
        getShoppingListUseCase.execute(
            onNext = {
                items.value = it
                isLoadingRefresh.value = false
            },
            onError = {
                mEvent.value = Event(ShoppingListEvent.ERROR)
            }
        )
    }


    override fun onCleared() {
        super.onCleared()
        deleteListUseCase.dispose()
        updateListUseCase.dispose()
        sortListUseCase.dispose()
        getShoppingListUseCase.dispose()
        addListUseCase.dispose()
    }

    fun addList(text: String) {
        addListUseCase.execute(params = text)
    }

    override fun onRecyclerClick(type: ClickType, pos: Int) {
        when (type) {
            RecyclerClickType.MAIN -> {
                currentItem = items.value?.get(pos)!!
                mEvent.value = Event(ShoppingListEvent.ON_ITEM_CLICK)
            }
            RecyclerClickType.REMOVE -> {
                deleteListUseCase.execute(params = items.value?.get(pos))
            }

            RecyclerClickType.ARCHIVE -> {
                updateListUseCase.execute(params = items.value?.get(pos)?.apply { isArchived=true })
            }
        }
    }

    fun sort() {
        items.value?.let { items ->
            isAscending = !isAscending
            sortListUseCase.execute(
                params = SortListUseCase.SortListParams(isAscending, items),
                onSuccess = {
                    this.items.value = it
                })
        }
    }


}