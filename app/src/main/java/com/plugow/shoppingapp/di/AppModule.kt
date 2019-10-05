package com.plugow.shoppingapp.di

import android.app.Application
import android.content.Context
import com.plugow.shoppingapp.db.LocalRepository
import com.plugow.shoppingapp.db.LocalRepositoryImpl
import com.plugow.shoppingapp.event.RxBus
import com.plugow.shoppingapp.util.AppSchedulerProvider
import com.plugow.shoppingapp.util.SchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun appSchedulerProvider(schedulerProvider: AppSchedulerProvider) : SchedulerProvider

    @Binds
    abstract fun localRepository(localRepositoryImpl: LocalRepositoryImpl) : LocalRepository

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun provideBus() : RxBus{
            return RxBus()
        }

    }
}