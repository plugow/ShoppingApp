package com.plugow.shoppingapp.domain.base

import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Single

abstract class SingleUseCase<Results : Any, in Params>(
    schedulerProvider: SchedulerProvider
) : BaseReactiveUseCase(schedulerProvider) {

    /**
     * Builds an [Single] which will be used when executing the current [SingleUseCase].
     */
    abstract fun buildUseCaseSingle(params: Params? = null): Single<Results>

    /**
     * Executes the current use case.
     *
     * @param onError [(Throwable) -> Unit] error callback from stream
     * @param onSuccess [(Results) -> Unit] completion callback from stream
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    fun execute(
        onError: (Throwable) -> Unit = onErrorStub,
        onSuccess: (Results) -> Unit = onNextStub,
        params: Params? = null
    ) {
        val single = buildUseCaseSingleWithSchedulers(params)
        addDisposable(single.subscribe(onSuccess, onError))
    }

    /**
     * Builds a [Single] which will be used when executing the current [SingleUseCase].
     * With provided Schedulers
     */
    private fun buildUseCaseSingleWithSchedulers(params: Params?): Single<Results> {
        return buildUseCaseSingle(params)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.uiScheduler())
    }
}
