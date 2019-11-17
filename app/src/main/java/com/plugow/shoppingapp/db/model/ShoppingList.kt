package com.plugow.shoppingapp.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "shoppingLists")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var createdAt: Date = Date(),
    var isArchived: Boolean = false,

    var productsAmount: Int = 0,
    var doneAmount: Int = 0
)
