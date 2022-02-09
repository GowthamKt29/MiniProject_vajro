package com.vajro.task.data.network

import com.vajro.task.model.ProductResponseDTO
import kotlinx.coroutines.flow.Flow

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
interface ApiHelper {
    suspend fun getProducts(): Flow<ProductResponseDTO>
}