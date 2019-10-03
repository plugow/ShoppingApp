package com.plugow.shoppingapp.di

import android.app.Application
import android.content.Context
import com.plugow.shoppingapp.db.AppRepo
import com.plugow.shoppingapp.db.dao.ProductDao
import com.plugow.shoppingapp.db.dao.SearchItemDao
import com.plugow.shoppingapp.db.dao.ShoppingListDao
import com.plugow.shoppingapp.event.RxBus
import com.plugow.shoppingapp.util.AppSchedulerProvider
import com.plugow.shoppingapp.util.SchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun appSchedulerProvider(schedulerProvider: AppSchedulerProvider) : SchedulerProvider

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