package com.plugow.shoppingapp.domain

import com.plugow.shoppingapp.db.LocalRepository
import com.plugow.shoppingapp.db.model.Product
import com.plugow.shoppingapp.domain.base.ObservableUseCase
import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    schedulerProvider: SchedulerProvider
) : ObservableUseCase<List<Product>, Int>(schedulerProvider) {
    override fun buildUseCaseObservable(params: Int?): Observable<List<Product>> {
        checkNotNull(params)
        return localRepository.getProductsById(params)
    }
}
