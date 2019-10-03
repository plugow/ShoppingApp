package com.plugow.shoppingapp.domain

import com.plugow.shoppingapp.db.AppRepo
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.domain.base.CompletableUseCase
import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Completable
import javax.inject.Inject

class UpdateListUseCase @Inject constructor(private val appRepo: AppRepo, schedulerProvider: SchedulerProvider) : CompletableUseCase<ShoppingList>(schedulerProvider) {
    override fun buildUseCaseCompletable(params: ShoppingList?) : Completable {
        checkNotNull(params)
        return appRepo.updateList(params)
    }
}