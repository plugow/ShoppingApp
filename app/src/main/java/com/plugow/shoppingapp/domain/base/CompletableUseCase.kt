package com.plugow.shoppingapp.domain.base

import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Completable

abstract class CompletableUseCase<in Params>(
    schedulerProvider: SchedulerProvider
) : BaseReactiveUseCase(schedulerProvider) {

    /**
     * Builds a [Completable] which will be used when executing the current [CompletableUseCase].
     */
    abstract fun buildUseCaseCompletable(params: Params? = null): Completable

    /**
     * Executes the current use case.
     *
     * @param onError [(Throwable) -> Unit] error callback from stream
     * @param onComplete [() -> Unit] completion callback from stream
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    fun execute(
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub,
        params: Params? = null
    ) {
        val completable = buildUseCaseCompletableWithSchedulers(params)
        addDisposable(completable.subscribe(onComplete, onError))
    }

    /**
     * Builds a [Completable] which will be used when executing the current [CompletableUseCase].
     * With provided Schedulers
     */
    private fun buildUseCaseCompletableWithSchedulers(params: Params?): Completable {
        return buildUseCaseCompletable(params)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.uiScheduler())
    }
}
