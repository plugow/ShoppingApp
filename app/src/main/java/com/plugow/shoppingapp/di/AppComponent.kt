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
    ActivityBindingModule::class, ViewModelModule::class, DbModule::class])
interface AppComponent : AndroidInjector<ShoppingApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
