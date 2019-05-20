package com.plugow.shoppingapp.db

import com.plugow.shoppingapp.db.model.*
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.rx2.kotlinextensions.rx
import com.raizlabs.android.dbflow.sql.language.OrderBy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

class AppRepo {
    fun getSearchItems() =
            (select from SearchItem::class)
                .rx().queryList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun addProducts(items:List<SearchItem>, shoppingListId:Int) =
        items.toObservable()
            .doOnNext { Product(name = it.name, shoppingListId = shoppingListId).save() }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getProductById(shoppingListId: Int) =
        (select from Product::class where Product_Table.shopping_list_id.eq(shoppingListId))
            .rx().queryList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun getShoppingList() =
        (select from ShoppingList::class where ShoppingList_Table.isArchived.eq(false) orderBy OrderBy.fromProperty(ShoppingList_Table.createdAt))
            .rx().queryList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun getArchivedList() =
        (select from ShoppingList::class where ShoppingList_Table.isArchived.eq(true) orderBy OrderBy.fromProperty(ShoppingList_Table.createdAt))
            .rx().queryList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

}