package com.plugow.shoppingapp.domain

import com.plugow.shoppingapp.db.LocalRepository
import com.plugow.shoppingapp.db.model.Product
import com.plugow.shoppingapp.domain.base.CompletableUseCase
import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Completable
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    schedulerProvider: SchedulerProvider
) : CompletableUseCase<Product>(schedulerProvider) {

    override fun buildUseCaseCompletable(params: Product?): Completable {
        checkNotNull(params)
        return localRepository.deleteProduct(params)
    }
}