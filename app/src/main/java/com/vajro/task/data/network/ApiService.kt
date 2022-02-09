package com.vajro.task.data.network

import com.vajro.task.model.ProductResponseDTO
import retrofit2.http.GET

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
interface ApiService {
    @GET("/v2/5def7b172f000063008e0aa2")
    suspend fun getProducts(): ProductResponseDTO
}