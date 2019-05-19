package com.plugow.shoppingapp.di

import com.plugow.shoppingapp.db.AppRepo
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class DBModule {

    @Provides
    @Reusable
    fun provideRepo() : AppRepo{
        return AppRepo()
    }
}