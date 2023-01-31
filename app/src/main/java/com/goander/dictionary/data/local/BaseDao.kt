package com.goander.dictionary.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert
    public suspend fun insert(t: T): Long
    
    @Insert
    public suspend fun insertAll(t: List<T>): List<Long>
    
    @Update
    public suspend fun update(t: T): Int

    @Update
    public suspend fun updateAll(t: List<T>): Int
    
    @Delete
    public suspend fun delete(t: T): Int

    @Delete
    public suspend fun deleteAll(t: List<T>): Int
    
}