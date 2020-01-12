package com.plugow.shoppingapp.db

import com.plugow.shoppingapp.db.model.Product
import com.plugow.shoppingapp.db.model.SearchItem
import com.plugow.shoppingapp.db.model.ShoppingList
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface LocalRepository {
    fun getSearchItems(): Single<List<SearchItem>>

    fun addProducts(items: List<SearchItem>, shoppingListId: Int): Completable

    fun getProductsById(shoppingListId: Int): Observable<List<Product>>

    fun updateProduct(product: Product): Completable

    fun deleteProduct(product: Product): Completable

    fun updateList(list: ShoppingList): Completable

    fun deleteList(list: ShoppingList): Completable

    fun insertList(list: ShoppingList): Completable

    fun getShoppingList(): Observable<List<ShoppingList>>

    fun getArchivedList(): Observable<List<ShoppingList>>

    fun getShoppingListById(shoppingListId: Int): Single<ShoppingList>

    fun increaseProductAmount(productId: Int): Completable

    fun decreaseProductAmount(productId: Int): Completable
}
