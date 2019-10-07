package com.plugow.shoppingapp.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.plugow.shoppingapp.db.model.SearchItem
import io.reactivex.Single


@Dao
interface SearchItemDao: BaseDao<SearchItem> {

    @Query("Select * from searchItems")
    fun getItems() : Single<List<SearchItem>>

    @Query("SELECT COUNT(*) FROM searchItems")
    fun count(): Int
}