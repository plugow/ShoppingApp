package com.plugow.shoppingapp.domain

import com.plugow.shoppingapp.db.LocalRepository
import com.plugow.shoppingapp.domain.base.CompletableUseCase
import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Completable
import javax.inject.Inject

class SubtractProductAmountUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    schedulerProvider: SchedulerProvider
) : CompletableUseCase<ProductId>(schedulerProvider) {

    override fun buildUseCaseCompletable(params: ProductId?): Completable {
        val id = checkNotNull(params?.id)
        return localRepository.decreaseProductAmount(id)
    }

}

