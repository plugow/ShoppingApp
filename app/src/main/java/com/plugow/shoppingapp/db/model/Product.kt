package com.plugow.shoppingapp.db.model

import com.plugow.shoppingapp.db.AppDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.ForeignKey
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table

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

    @ForeignKey(stubbedRelationship = true)
    var shoppingList: ShoppingList?=null

)