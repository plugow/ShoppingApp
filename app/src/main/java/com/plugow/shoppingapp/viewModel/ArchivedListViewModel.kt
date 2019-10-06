package com.plugow.shoppingapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.domain.DeleteListUseCase
import com.plugow.shoppingapp.domain.GetArchivedListUseCase
import com.plugow.shoppingapp.domain.SortListUseCase
import com.plugow.shoppingapp.domain.UpdateListUseCase
import com.plugow.shoppingapp.trait.RefreshableList
import com.plugow.shoppingapp.ui.adapter.ArchiveClickType
import com.plugow.shoppingapp.ui.adapter.ClickType
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ArchivedListViewModel @Inject constructor(
    private val deleteListUseCase: DeleteListUseCase,
    private val updateListUseCase: UpdateListUseCase,
    private val getArchivedListUseCase: GetArchivedListUseCase,
    private val sortListUseCase: SortListUseCase
) : ViewModel(), RefreshableList<ShoppingList> {
    override var items: MutableLiveData<List<ShoppingList>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    var isAscending = false


    override fun onRecyclerClick(type: ClickType, pos: Int) {
        when (type) {
            ArchiveClickType.REMOVE -> {
                deleteListUseCase.execute(params = items.value?.get(pos))
            }
            ArchiveClickType.RESTORE -> {
                updateListUseCase.execute(params = items.value?.get(pos))
            }
        }
    }

    override fun loadItems() {
        getArchivedListUseCase.execute(
            onNext = {
                items.value = it
                isLoadingRefresh.value = false
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        deleteListUseCase.dispose()
        updateListUseCase.dispose()
        sortListUseCase.dispose()
        getArchivedListUseCase.dispose()
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