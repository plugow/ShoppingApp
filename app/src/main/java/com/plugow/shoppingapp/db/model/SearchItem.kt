package com.plugow.shoppingapp.db.model

import com.plugow.shoppingapp.db.AppDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table

@Table(database = AppDB::class)
class SearchItem (
    @PrimaryKey(autoincrement = true)
    @Column
    var id:Int=-1,

    @Column
    var name:String="",

    @Column
    var isChosen:Boolean = false


)