package com.plugow.shoppingapp.domain

import com.plugow.shoppingapp.db.model.SearchItem
import com.plugow.shoppingapp.domain.base.SingleUseCase
import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable
import javax.inject.Inject

class FilterSearchItemsUseCase @Inject constructor(schedulerProvider: SchedulerProvider) :
    SingleUseCase<List<SearchItem>, FilterSearchItemsUseCase.FilterSearchItemsParam>(
        schedulerProvider
    ) {
    override fun buildUseCaseSingle(params: FilterSearchItemsParam?): Single<List<SearchItem>> {
        checkNotNull(params)
        return params.items.toObservable()
            .filter { it.name.toLowerCase().contains(params.text) }
            .toList()
    }

    class FilterSearchItemsParam(val text: String, val items: List<SearchItem>)
}
