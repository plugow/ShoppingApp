package com.plugow.shoppingapp.di

import com.plugow.shoppingapp.db.AppRepo
import dagger.Module
import dagger.Provides

@Module
class DBModule {

    @Provides
    fun provideRepo() : AppRepo{
        return AppRepo()
    }
}