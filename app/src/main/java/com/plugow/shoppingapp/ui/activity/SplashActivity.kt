package com.plugow.shoppingapp.ui.activity

import android.app.Activity
import android.os.Bundle
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import org.jetbrains.anko.startActivity

class SplashActivity : Activity() {
    private lateinit var disposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable = Single.just("splash")
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { startActivity<MainActivity>() },
                        onError = { startActivity<MainActivity>() }
                )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
