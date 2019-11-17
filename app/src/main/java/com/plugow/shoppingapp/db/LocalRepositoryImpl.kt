package com.plugow.shoppingapp.db

import com.plugow.shoppingapp.db.dao.ProductDao
import com.plugow.shoppingapp.db.dao.SearchItemDao
import com.plugow.shoppingapp.db.dao.ShoppingListDao
import com.plugow.shoppingapp.db.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val productsDao: ProductDao,
    private val listDao: ShoppingListDao,
    private val itemsDao: SearchItemDao
) : LocalRepository {
    override fun getSearchItems() =
        itemsDao.getItems()

    override fun addProducts(items: List<SearchItem>, shoppingListId: Int) =
        items.toObservable()
            .map { Product(name = it.name, shoppingListId = shoppingListId) }
            .toList()
            .flatMapCompletable {
                productsDao.bulbInsert(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getProductsById(shoppingListId: Int) =
        productsDao.getProductsByList(shoppingListId).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()
        )

    override fun updateProduct(product: Product): Completable {
        return productsDao.update(product)
    }

    override fun deleteProduct(product: Product): Completable {
        return productsDao.delete(product)
    }

    override fun updateList(list: ShoppingList) = listDao.update(list)

    override fun deleteList(list: ShoppingList) = listDao.delete(list)

    override fun insertList(list: ShoppingList) =
        listDao.insert(list)

    override fun getShoppingList() =
        listDao.getListsWithProducts()
            .flatMap {
                Observable.fromIterable(it)
                    .map { fillShoppingList(it) }
                    .toList().toObservable()
            }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun getArchivedList() =
        listDao.getArchivedListsWithProducts()
            .flatMap {
                Observable.fromIterable(it)
                    .map { fillShoppingList(it) }.toList().toObservable()
            }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun getShoppingListById(shoppingListId: Int) =
        listDao.getListById(shoppingListId)
            .map { fillShoppingList(it) }
            .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())

    private fun fillShoppingList(shoppingListWithProducts: ShoppingListWithProducts): ShoppingList {
        shoppingListWithProducts.shoppingList?.productsAmount =
            shoppingListWithProducts.products.size
        shoppingListWithProducts.shoppingList?.doneAmount =
            shoppingListWithProducts.products.filter { it.isDone }.size
        return shoppingListWithProducts.shoppingList!!
    }
}
