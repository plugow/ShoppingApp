package com.plugow.shoppingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.db.model.SearchItem
import com.plugow.shoppingapp.domain.AddProductsInListUseCase
import com.plugow.shoppingapp.domain.FilterSearchItemsUseCase
import com.plugow.shoppingapp.domain.GetSearchItemsUseCase
import com.plugow.shoppingapp.event.SearchEvent
import com.plugow.shoppingapp.trait.RefreshableList
import com.plugow.shoppingapp.ui.adapter.ClickType
import com.plugow.shoppingapp.ui.adapter.SearchClickType
import com.plugow.shoppingapp.util.Event
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchItemsUseCase: GetSearchItemsUseCase,
    private val addProductsInListUseCase: AddProductsInListUseCase,
    private val filterSearchItemsUseCase: FilterSearchItemsUseCase
) : ViewModel(), RefreshableList<SearchItem> {
    override var items: MutableLiveData<List<SearchItem>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    var customItemVisibility = MutableLiveData(false)
    var isCustomItemSelected = MutableLiveData(false)
    var searchBar: MutableLiveData<String> = MutableLiveData("")
    var selectedItems = arrayListOf<SearchItem>()
    var shoppingListId: Int = 0
    lateinit var mItems: List<SearchItem>
    private val mEvent: MutableLiveData<Event<SearchEvent>> = MutableLiveData()
    val event: LiveData<Event<SearchEvent>>
        get() = mEvent

    var onClickListener: MutableLiveData<(ClickType, Int) -> Unit> = MutableLiveData { type, pos ->
        onRecyclerClick(type, pos)
    }

    override fun onCleared() {
        super.onCleared()
        getSearchItemsUseCase.dispose()
        addProductsInListUseCase.dispose()
        filterSearchItemsUseCase.dispose()
    }

    override fun loadItems() {
        getSearchItemsUseCase.execute(onSuccess = {
            mItems = it
            items.value = mItems
            isLoadingRefresh.value = false
        })
    }

    override fun onRecyclerClick(type: ClickType, pos: Int) {
        when (type) {
            SearchClickType.ADD -> {
                items.value?.get(pos)?.let { selectedItems.add(it) }
            }
            SearchClickType.REMOVE -> {
                items.value?.get(pos)?.let { selectedItems.remove(it) }
            }
        }
    }

    fun onReadyClick() {
        if (selectedItems.size != 0) {
            addProductsInListUseCase.execute(
                params = AddProductsInListUseCase.AddProductsInListParam(
                    selectedItems,
                    shoppingListId
                ), onComplete = {
                    mEvent.value = Event(SearchEvent.DISMISS)
                })
        } else {
            mEvent.value = Event(SearchEvent.DISMISS)
        }
    }

    fun onSearchTextChanged() {
        searchBar.value?.let {
            filterSearchItemsUseCase.execute(
                params = FilterSearchItemsUseCase.FilterSearchItemsParam(
                    it,
                    mItems
                ), onSuccess = { filteredItems ->
                    items.value = filteredItems
                    customItemVisibility.value = it.trim() != ""
                })
        }
    }

    fun onCustomItemClicked() {
        isCustomItemSelected.value = !isCustomItemSelected.value!!
        if (isCustomItemSelected.value!!) {
            selectedItems.add(SearchItem(name = searchBar.value!!, isChosen = true))
        } else {
            selectedItems.removeAll { it.name == searchBar.value!! }
        }
    }
}
