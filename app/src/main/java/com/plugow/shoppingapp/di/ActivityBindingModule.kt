package com.plugow.shoppingapp.di

import com.plugow.shoppingapp.di.scope.ActivityScoped
import com.plugow.shoppingapp.ui.MainActivity
import com.plugow.shoppingapp.ui.ShoppingListDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun mainActivity() : MainActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun shoppingDetailActivity() : ShoppingListDetailActivity
}