package com.plugow.shoppingapp.di

import android.app.Application
import android.content.Context
import com.plugow.shoppingapp.db.AppRepo
import com.plugow.shoppingapp.db.dao.ProductDao
import com.plugow.shoppingapp.db.dao.SearchItemDao
import com.plugow.shoppingapp.db.dao.ShoppingListDao
import com.plugow.shoppingapp.event.RxBus
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Binds
    abstract fun bindContext(application: Application): Context

    @Module
    companion object {

        @Provides
        @Reusable
        @JvmStatic
        fun provideRepo(shoppingListDao: ShoppingListDao, productDao: ProductDao, searchItemDao: SearchItemDao) : AppRepo {
            return AppRepo(productDao, shoppingListDao, searchItemDao)
        }

        @Provides
        @JvmStatic
        @Singleton
        fun provideBus() : RxBus{
            return RxBus()
        }

    }
}