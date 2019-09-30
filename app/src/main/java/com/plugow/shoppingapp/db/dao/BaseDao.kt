package com.plugow.shoppingapp.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import io.reactivex.Completable

interface BaseDao<T> {
    @Insert
    fun insert(vararg t:T): Completable

    @Insert
    fun bulbInsert(list:List<T>):Completable

    @Update
    fun update(vararg t:T): Completable

    @Delete
    fun delete(vararg t:T): Completable
}