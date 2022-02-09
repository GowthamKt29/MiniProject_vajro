package com.vajro.task.data.network



import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
object RetrofitBuilder {
    const val BASE_URL = "https://www.mocky.io/"
    private var httpClient = OkHttpClient().newBuilder()
        .connectTimeout(90, TimeUnit.SECONDS)
        .readTimeout(90, TimeUnit.SECONDS)
        .writeTimeout(90, TimeUnit.SECONDS)
        .build();

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}