package com.plugow.shoppingapp.domain

import com.plugow.shoppingapp.db.AppRepo
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.domain.base.ObservableUseCase
import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

class GetShoppingListUseCase @Inject constructor(val repo: AppRepo, schedulerProvider: SchedulerProvider) : ObservableUseCase<List<ShoppingList>,Unit> (schedulerProvider) {
    override fun buildUseCaseObservable(params: Unit?): Observable<List<ShoppingList>> {
        return repo.getShoppingList()
    }
}