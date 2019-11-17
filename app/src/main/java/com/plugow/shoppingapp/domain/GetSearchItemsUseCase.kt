package com.plugow.shoppingapp.domain

import com.plugow.shoppingapp.db.LocalRepository
import com.plugow.shoppingapp.db.model.SearchItem
import com.plugow.shoppingapp.domain.base.SingleUseCase
import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class GetSearchItemsUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    schedulerProvider: SchedulerProvider
) : SingleUseCase<List<SearchItem>, Unit>(schedulerProvider) {
    override fun buildUseCaseSingle(params: Unit?): Single<List<SearchItem>> {
        return localRepository.getSearchItems()
    }
}
