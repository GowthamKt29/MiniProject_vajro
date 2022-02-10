package com.vajro.task.data.local


import com.vajro.task.data.local.entity.CartItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by gowtham.ashok on 2/10/2022.
 */
interface DatabaseHelper {

    fun insertSingle(cartItem: CartItem):Flow<Unit>
    fun updateCart(id: Int?,quantity:Int?):Flow<Unit>
    fun deleteItem(proId: Int?):Flow<Unit>
    fun getCount():Flow<Int>
    fun getAllProducts():Flow<List<CartItem>>

}