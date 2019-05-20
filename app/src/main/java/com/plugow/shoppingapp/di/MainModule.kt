package com.plugow.shoppingapp.di

import com.plugow.shoppingapp.di.scope.FragmentScoped
import com.plugow.shoppingapp.ui.fragment.ArchivedListFragment
import com.plugow.shoppingapp.ui.fragment.ShoppingListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun shoppingListFragment() : ShoppingListFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun archivedListFragment() : ArchivedListFragment
}