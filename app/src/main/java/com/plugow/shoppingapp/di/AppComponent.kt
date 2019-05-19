package com.plugow.shoppingapp.di

import android.app.Application
import com.plugow.shoppingapp.ShoppingApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class,
    ActivityBindingModule::class, ViewModelModule::class, DBModule::class])
interface AppComponent : AndroidInjector<ShoppingApp> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}