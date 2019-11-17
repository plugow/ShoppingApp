package com.plugow.shoppingapp.domain.base

import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.plugins.RxJavaPlugins

abstract class BaseReactiveUseCase(protected val schedulerProvider: SchedulerProvider) {
    /**
     * Default values for observable callbacks
     */
    protected val onNextStub: (Any) -> Unit = {}
    protected val onCompleteStub: () -> Unit = {}
    protected val onErrorStub: (Throwable) -> Unit = {
        RxJavaPlugins.onError(
            OnErrorNotImplementedException(it)
        )
    }

    private val disposables = CompositeDisposable()

    open fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}
