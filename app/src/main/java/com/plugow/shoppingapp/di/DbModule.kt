package com.plugow.shoppingapp.di

import android.content.Context
import com.plugow.shoppingapp.db.AppDB
import com.plugow.shoppingapp.db.dao.ProductDao
import com.plugow.shoppingapp.db.dao.SearchItemDao
import com.plugow.shoppingapp.db.dao.ShoppingListDao
import dagger.Module
import dagger.Provides

@Module
object DbModule {

    @Provides
    @JvmStatic
    fun provideDb(context:Context) : AppDB = AppDB.getInstance(context)

    @Provides
    @JvmStatic
    fun provideProductDao(appDB: AppDB):ProductDao = appDB.productDao()

    @Provides
    @JvmStatic
    fun provideListDao(appDB: AppDB):ShoppingListDao = appDB.listDao()

    @Provides
    @JvmStatic
    fun provideItemDao(appDB: AppDB):SearchItemDao = appDB.itemsDao()
}