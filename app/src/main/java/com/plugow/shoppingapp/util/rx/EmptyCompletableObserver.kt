package com.plugow.shoppingapp.util.rx

import io.reactivex.observers.DisposableCompletableObserver

class EmptyCompletableObserver : DisposableCompletableObserver() {

    override fun onComplete() {}

    override fun onError(e: Throwable) {}
}
