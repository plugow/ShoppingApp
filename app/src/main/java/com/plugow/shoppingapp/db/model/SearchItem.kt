package com.plugow.shoppingapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchItems")
class SearchItem (
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var name:String="",
    @ColumnInfo(defaultValue = "0")
    var isChosen:Boolean = false
)