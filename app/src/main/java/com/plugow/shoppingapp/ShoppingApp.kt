package com.plugow.shoppingapp

import com.plugow.shoppingapp.db.AppDB
import com.plugow.shoppingapp.di.DaggerAppComponent
import com.raizlabs.android.dbflow.config.DatabaseConfig
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.runtime.ContentResolverNotifier
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class ShoppingApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()
        FlowManager.init(
            FlowConfig.Builder(this)
                .addDatabaseConfig(
                    DatabaseConfig.Builder(AppDB::class.java)
                        .modelNotifier(ContentResolverNotifier(BuildConfig.APPLICATION_ID))
                        .build()
                ).build()
        )

    }
}