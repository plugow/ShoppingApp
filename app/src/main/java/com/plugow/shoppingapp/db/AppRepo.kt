package com.plugow.shoppingapp.db

import com.plugow.shoppingapp.db.model.SearchItem
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.rx2.kotlinextensions.rx
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppRepo {

//
    fun getSearchItems() =
            (select from SearchItem::class).rx().queryList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//
//    fun getSingleDeliveryByClientId(clientId:Long, id: Long) =
//            (select from Delivery::class where Delivery_Table.clientId.eq(clientId) and Delivery_Table.id.eq(id)).rx().querySingle().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

}