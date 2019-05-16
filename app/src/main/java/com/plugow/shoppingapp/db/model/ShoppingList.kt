package com.plugow.shoppingapp.db.model

import com.plugow.shoppingapp.db.AppDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import com.raizlabs.android.dbflow.annotation.OneToMany
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.sql.language.SQLite


@Table(database = AppDB::class)
class ShoppingList(
    @Column
    @PrimaryKey(autoincrement = true)
    var id:Int = -1,
    @Column
    var name:String = "",

    var products:List<Product>?=null
) : BaseModel(){

    @OneToMany(methods = [OneToMany.Method.ALL], variableName = "products")
    fun oneToManyProducts(): List<Product> {
        if (products == null) {
            products = SQLite.select()
                .from(Product::class.java)
                .where(Product_Table.shoppingList_id.eq(id))
                .queryList()
        }
        return products as List<Product>
    }

    override fun save(): Boolean {
        val res = super.save()
        products?.let {
            for (p in it) {
                p.shoppingList = this
                p.save()
            }
        }
        return res
    }
}