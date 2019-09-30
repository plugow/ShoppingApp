package com.plugow.shoppingapp.db.dao

import androidx.room.*
import com.plugow.shoppingapp.db.model.Product
import io.reactivex.Observable

@Dao
interface ProductDao: BaseDao<Product> {

    @Query("Select * from products where shoppingListId=:shoppingListId")
    fun getProductsByList(shoppingListId:Int) : Observable<List<Product>>
}