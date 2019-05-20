package com.plugow.shoppingapp.di

import com.plugow.shoppingapp.di.scope.FragmentScoped
import com.plugow.shoppingapp.ui.dialog.SearchDialogFragment
import com.plugow.shoppingapp.ui.fragment.ArchivedListFragment
import com.plugow.shoppingapp.ui.fragment.ShoppingListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun searchDialogFragment() : SearchDialogFragment
}