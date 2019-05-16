package com.plugow.shoppingapp.db

import com.raizlabs.android.dbflow.annotation.Database

@Database(version = AppDB.VERSION, name = AppDB.NAME)
object AppDB {
    const val NAME = "AppDB"
    const val VERSION = 1
}