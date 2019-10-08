package com.plugow.shoppingapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "products", foreignKeys = [ForeignKey(entity = ShoppingList::class, parentColumns = ["id"], childColumns = ["shoppingListId"], onDelete = CASCADE)])
data class Product (
    @PrimaryKey(autoGenerate = true) var id:Int=0,

    var name:String="",
    var amount:Int = 1,
    var isDone:Boolean = false,
    @ColumnInfo(name = "shoppingListId", index = true)
    var shoppingListId:Int = -1
)