package com.plugow.shoppingapp.domain.base

import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Observable

abstract class ObservableUseCase<Results:Any, in Params>(schedulerProvider: SchedulerProvider) :
    BaseReactiveUseCase(schedulerProvider) {

    /**
     * Builds an [Observable] which will be used when executing the current [ObservableUseCase].
     */
    abstract fun buildUseCaseObservable(params: Params? = null): Observable<Results>

    /**
     * Executes the current use case.
     *
     * @param onError [(Throwable) -> Unit] error callback from stream
     * @param onComplete [() -> Unit] completion callback from stream
     * @param onNext [(Results) -> Unit] next observable callback from stream
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    fun execute(
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub,
        onNext: (Results) -> Unit = onNextStub, params: Params? = null
    ) {
        val observable = buildUseCaseObservableWithSchedulers(params)
        addDisposable(observable.subscribe(onNext, onError, onComplete))
    }

    /**
     * Builds an [Observable] which will be used when executing the current [ObservableUseCase].
     * With provided Schedulers
     */
    private fun buildUseCaseObservableWithSchedulers(params: Params?): Observable<Results> {
        return buildUseCaseObservable(params)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.uiScheduler())
    }
}
