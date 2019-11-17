package com.plugow.shoppingapp.util

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingComponent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchBindingComponent(val lifecycle: Lifecycle) : DataBindingComponent, LifecycleObserver {
    private var compositeDisposable = CompositeDisposable()

    init {
        lifecycle.addObserver(this)
    }

    override fun getSearchBindingComponent(): SearchBindingComponent {
        return this
    }

    @BindingAdapter("textChanged")
    fun bindSearchBar(editText: EditText, listener: () -> Unit) {
        editText.afterTextChangeEvents()
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .subscribe {
                listener.invoke()
            }.addTo(compositeDisposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun dispose() {
        compositeDisposable.clear()
        lifecycle.removeObserver(this)
    }
}
