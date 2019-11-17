package com.plugow.shoppingapp.db.dao

import androidx.room.*
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.db.model.ShoppingListWithProducts
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ShoppingListDao : BaseDao<ShoppingList> {

    @Query("Select * from shoppingLists where not(isArchived) order by createdAt")
    fun getLists(): Maybe<List<ShoppingList>>

    @Query("Select * from shoppingLists where isArchived order by createdAt")
    fun getArchivedLists(): Maybe<List<ShoppingList>>

    @Transaction
    @Query("Select * from shoppingLists where id = :shoppingListId")
    fun getListById(shoppingListId: Int): Single<ShoppingListWithProducts>

    @Transaction
    @Query("Select * from shoppingLists where not(isArchived) order by createdAt")
    fun getListsWithProducts(): Observable<List<ShoppingListWithProducts>>

    @Transaction
    @Query("Select * from shoppingLists where isArchived order by createdAt")
    fun getArchivedListsWithProducts(): Observable<List<ShoppingListWithProducts>>
}
