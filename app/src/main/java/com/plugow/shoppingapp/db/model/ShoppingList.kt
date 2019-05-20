package com.plugow.shoppingapp.db.model

import com.plugow.shoppingapp.db.AppDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import com.raizlabs.android.dbflow.annotation.OneToMany
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.sql.language.SQLite
import java.util.*


@Table(database = AppDB::class)
class ShoppingList(
    @Column
    @PrimaryKey(autoincrement = true)
    var id:Int = -1,
    @Column
    var name:String = "",

    @Column
    var createdAt: Date = Date(),

    @Column
    var isArchived:Boolean = false,

    var products:List<Product>?=null,

    var productsAmount:Int = 0,
    var doneAmount:Int = 0
) : BaseModel(){

    @OneToMany(methods = [OneToMany.Method.ALL], variableName = "products")
    fun oneToManyProducts(): List<Product> {
        if (products == null) {
            products = SQLite.select()
                .from(Product::class.java)
                .where(Product_Table.shopping_list_id.eq(id))
                .queryList()
        }
        return products as List<Product>
    }

    override fun save(): Boolean {
        val res = super.save()
        products?.let {
            for (p in it) {
                p.shoppingListId = this.id
                p.save()
            }
        }
        return res
    }
}