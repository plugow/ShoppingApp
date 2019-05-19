package com.plugow.shoppingapp.db.model

import com.plugow.shoppingapp.db.AppDB
import com.raizlabs.android.dbflow.annotation.*

@Table(database = AppDB::class)
class Product (
    @PrimaryKey(autoincrement = true)
    @Column
    var id:Int=-1,

    @Column
    var name:String="",

    @Column
    var amount:Int = 1,

    @Column
    var isDone:Boolean = false,

    @Column
    @ForeignKey(tableClass = ShoppingList::class, references = [ForeignKeyReference(
        columnName = "shopping_list_id", foreignKeyColumnName = "id")])
    var shoppingListId:Int?=null

)