package com.vajro.task.data.network

import com.vajro.task.model.ProductResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
class ApiRepository(
    private val apiService: ApiService,
    private val coroutineContext: CoroutineContext
) : ApiHelper {
    override suspend fun getProducts(): Flow<ProductResponseDTO> {
        return flow {
            emit(apiService.getProducts())
        }.flowOn(coroutineContext)
    }


}