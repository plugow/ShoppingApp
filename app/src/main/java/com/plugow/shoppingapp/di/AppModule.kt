package com.plugow.shoppingapp.di

import android.app.Application
import android.content.Context
import com.plugow.shoppingapp.db.AppRepo
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
        fun provideRepo() : AppRepo {
            return AppRepo()
        }

        @Provides
        @JvmStatic
        @Singleton
        fun provideBus() : RxBus{
            return RxBus()
        }

    }
}