package com.plugow.shoppingapp.domain

import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.domain.base.SingleUseCase
import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class SortListUseCase @Inject constructor(schedulerProvider: SchedulerProvider) :
    SingleUseCase<List<ShoppingList>, SortListUseCase.SortListParams>(schedulerProvider) {
    override fun buildUseCaseSingle(params: SortListParams?): Single<List<ShoppingList>> {
        checkNotNull(params)
        return Single.fromCallable {
            if (params.isAscending) {
                params.list.toMutableList().sortedBy { it.createdAt }.toList()
            } else {
                params.list.toMutableList().sortedByDescending { it.createdAt }.toList()
            }
        }
    }

    class SortListParams(val isAscending: Boolean, val list: List<ShoppingList>)
}
