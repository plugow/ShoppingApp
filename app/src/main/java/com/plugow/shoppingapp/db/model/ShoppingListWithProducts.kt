package com.plugow.shoppingapp.db.model

import androidx.room.Embedded
import androidx.room.Relation

class ShoppingListWithProducts {
    @Embedded
    var shoppingList: ShoppingList? = null
    @Relation(
        parentColumn = "id",
        entityColumn = "shoppingListId"
    )
    var products: List<Product> = ArrayList()
}
