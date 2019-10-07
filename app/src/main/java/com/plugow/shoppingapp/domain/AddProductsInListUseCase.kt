package com.plugow.shoppingapp.domain

import com.plugow.shoppingapp.db.LocalRepository
import com.plugow.shoppingapp.db.model.SearchItem
import com.plugow.shoppingapp.domain.base.CompletableUseCase
import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Completable
import javax.inject.Inject

class AddProductsInListUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    schedulerProvider: SchedulerProvider
) : CompletableUseCase<AddProductsInListUseCase.AddProductsInListParam>(schedulerProvider) {
    override fun buildUseCaseCompletable(params: AddProductsInListParam?): Completable {
        checkNotNull(params)

        return localRepository.addProducts(params.products, params.shoppingListId)
    }


    class AddProductsInListParam(val products:List<SearchItem>, val shoppingListId:Int)
}