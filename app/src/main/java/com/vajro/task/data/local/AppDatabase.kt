package com.vajro.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vajro.task.data.local.dao.CartDao
import com.vajro.task.data.local.entity.CartItem

/**
 * Created by gowtham.ashok on 2/10/2022.
 */
@Database(entities = [CartItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

}