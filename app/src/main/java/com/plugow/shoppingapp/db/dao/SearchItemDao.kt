package com.plugow.shoppingapp.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.plugow.shoppingapp.db.model.SearchItem
import io.reactivex.Maybe
import androidx.room.RoomMasterTable.TABLE_NAME



@Dao
interface SearchItemDao: BaseDao<SearchItem> {

    @Query("Select * from searchItems")
    fun getItems() : Maybe<List<SearchItem>>

    @Query("SELECT COUNT(*) FROM searchItems")
    fun count(): Int
}