package com.vajro.task.data.local


import com.vajro.task.data.local.entity.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

/**
 * Created by gowtham.ashok on 2/10/2022.
 */
class DatabaseRepoository(
    private val appDatabase: AppDatabase,
    private val coroutineContext: CoroutineContext
) : DatabaseHelper {
    override fun insertSingle(cartItem: CartItem) = flow {
        appDatabase.cartDao().insertSingle(cartItem)
        emit(Unit)
    }.flowOn(coroutineContext)

    override fun updateCart(id: Int?, quantity: Int?) = flow {
        appDatabase.cartDao().update(id, quantity)
        emit(Unit)
    }.flowOn(coroutineContext)

    override fun deleteItem(proId: Int?) = flow {
        appDatabase.cartDao().deleteByProductId(proId)
        emit(Unit)
    }.flowOn(coroutineContext)

    override fun getCount() = flow {
        emit(appDatabase.cartDao().getCount())
    }.flowOn(coroutineContext)

    override fun getAllProducts(): Flow<List<CartItem>> {
        return flow {
            emit(appDatabase.cartDao().getAll())
        }.flowOn(coroutineContext)
    }
}