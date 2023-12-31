package com.mariela.stores

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(StoreEntity::class), version = 2)
abstract class StoreDataBase:RoomDatabase() {
    abstract fun storeDao():StoreDao
}