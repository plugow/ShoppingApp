package com.plugow.shoppingapp.di

import com.plugow.shoppingapp.di.scope.ActivityScoped
import com.plugow.shoppingapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun mainActivity() : MainActivity
}