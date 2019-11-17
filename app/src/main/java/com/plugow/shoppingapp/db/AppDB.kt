package com.plugow.shoppingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.dao.ProductDao
import com.plugow.shoppingapp.db.dao.SearchItemDao
import com.plugow.shoppingapp.db.dao.ShoppingListDao
import com.plugow.shoppingapp.db.model.Product
import com.plugow.shoppingapp.db.model.SearchItem
import com.plugow.shoppingapp.db.model.ShoppingList
import java.util.concurrent.Executors

@Database(version = 1, entities = [Product::class, SearchItem::class, ShoppingList::class])
@TypeConverters(Converters::class)
abstract class AppDB : RoomDatabase() {

    abstract fun listDao(): ShoppingListDao
    abstract fun productDao(): ProductDao
    abstract fun itemsDao(): SearchItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        fun getInstance(context: Context): AppDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): AppDB {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDB::class.java,
                "ShoppingApp.db"
            )
                .build()
            instance.insertFromFile(context, instance.openHelper.writableDatabase)
            return instance
        }
    }

    // TODO do better implementataion
    fun insertFromFile(context: Context, db: SupportSQLiteDatabase) {
        Executors.newSingleThreadExecutor().execute {
            if (itemsDao().count() == 0) {
                val insertsStream = context.resources.openRawResource(R.raw.init_db)
                val inputAsString = insertsStream.bufferedReader().use { it.readText() }
                db.execSQL(inputAsString)
            }
        }
    }
}
