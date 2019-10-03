package com.plugow.shoppingapp.db

import com.plugow.shoppingapp.db.dao.ProductDao
import com.plugow.shoppingapp.db.dao.SearchItemDao
import com.plugow.shoppingapp.db.dao.ShoppingListDao
import com.plugow.shoppingapp.db.model.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

class AppRepo(
    private val productsDao: ProductDao,
    private val listDao: ShoppingListDao,
    private val itemsDao: SearchItemDao
) {
    fun getSearchItems() =
        itemsDao.getItems()

    fun addProducts(items: List<SearchItem>, shoppingListId: Int) =
        items.toObservable()
            .map { Product(name = it.name, shoppingListId = shoppingListId) }
            .toList()
            .flatMapCompletable {
                productsDao.bulbInsert(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getProductsById(shoppingListId: Int) =
        productsDao.getProductsByList(shoppingListId).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()
        )

    fun updateProduct(product: Product) {
        productsDao.update(product)
    }

    fun deleteProduct(product: Product) {
        productsDao.delete(product)
    }

    fun updateList(list: ShoppingList) = listDao.update(list)

    fun deleteList(list: ShoppingList) = listDao.delete(list)

    fun insertList(list: ShoppingList) =
        listDao.insert(list)

    fun getShoppingList() =
        listDao.getListsWithProducts()
            .flatMap {
                Observable.fromIterable(it)
                    .map { fillShoppingList(it) }
                    .toList().toObservable()
            }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun getArchivedList() =
        listDao.getArchivedListsWithProducts()
            .flatMap {
                Observable.fromIterable(it)
                    .map { fillShoppingList(it) }.toList().toObservable()
            }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    private fun fillShoppingList(shoppingListWithProducts: ShoppingListWithProducts): ShoppingList {
        shoppingListWithProducts.shoppingList?.productsAmount =
            shoppingListWithProducts.products.size
        shoppingListWithProducts.shoppingList?.doneAmount =
            shoppingListWithProducts.products.filter { it.isDone }.size
        return shoppingListWithProducts.shoppingList!!
    }


    fun getShoppingListById(shoppingListId: Int) =
        listDao.getListById(shoppingListId)
            .toObservable()
            .map { fillShoppingList(it) }
            .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())

}