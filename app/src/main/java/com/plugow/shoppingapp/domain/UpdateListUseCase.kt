package com.plugow.shoppingapp.domain

import com.plugow.shoppingapp.db.LocalRepository
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.domain.base.CompletableUseCase
import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Completable
import javax.inject.Inject

class UpdateListUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    schedulerProvider: SchedulerProvider
) : CompletableUseCase<ShoppingList>(schedulerProvider) {
    override fun buildUseCaseCompletable(params: ShoppingList?): Completable {
        checkNotNull(params)
        return localRepository.updateList(params)
    }
}
