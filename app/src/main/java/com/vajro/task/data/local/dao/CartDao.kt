package com.vajro.task.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vajro.task.data.local.entity.CartItem

/**
 * Created by gowtham.ashok on 2/10/2022.
 */
@Dao
interface CartDao {

    @Query("SELECT * FROM cartitem")
    fun getAll(): List<CartItem>

    @Query("SELECT COUNT(productId) FROM cartitem")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingle(cartItem: CartItem)

    @Query("DELETE FROM cartitem WHERE productId = :proId")
    fun deleteByProductId(proId: Int?)

    @Query("UPDATE cartitem SET quantity=:quantity WHERE productId = :id")
    fun update( id: Int?,quantity:Int?)
}