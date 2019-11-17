package com.plugow.shoppingapp.event
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBus {
    private val subject = PublishSubject.create<BusEvent>()

    fun emitEvent(event: BusEvent) {
        subject.onNext(event)
    }

    fun getEventObservable(): Observable<BusEvent> {
        return subject
    }
}
