package com.plugow.shoppingapp.di

import com.plugow.shoppingapp.di.scope.FragmentScoped
import com.plugow.shoppingapp.ui.dialog.SearchDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun searchDialogFragment(): SearchDialogFragment
}
