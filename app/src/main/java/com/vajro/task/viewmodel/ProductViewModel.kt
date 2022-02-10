package com.vajro.task.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.vajro.task.data.local.DatabaseHelper
import com.vajro.task.data.local.entity.CartItem
import com.vajro.task.data.network.ApiHelper
import com.vajro.task.model.ProductResponseDTO
import com.vajro.task.utils.ErrorResponseResult
import com.vajro.task.utils.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
class ProductViewModel(private val apiHelper: ApiHelper?, private val dbHelper: DatabaseHelper?) :
    ViewModel() {
    private val products: MutableLiveData<Resource<ProductResponseDTO>> by lazy {
        MutableLiveData<Resource<ProductResponseDTO>>()
    }
    val getProducts: LiveData<Resource<ProductResponseDTO>> get() = products

    private val cartCount: MutableLiveData<Resource<Int>> by lazy {
        MutableLiveData<Resource<Int>>()
    }
    val getCount: LiveData<Resource<Int>> get() = cartCount

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            products.postValue(Resource.loading(null))
            apiHelper?.getProducts()?.catch { e ->
                when (e) {
                    is HttpException -> {
                        val jsonObject = JSONObject(e.response()?.errorBody()?.string())
                        val errorResponseResult = Gson().fromJson(
                            jsonObject.toString(),
                            ErrorResponseResult::class.java
                        )
                        products.postValue(errorResponseResult.message?.let {
                            Resource.error(
                                it,
                                null
                            )
                        })

                    }
                    is SocketTimeoutException -> {}
                    else -> {
                        products.postValue(Resource.error(e.toString(), null))
                    }
                }
            }?.collect {
                products.postValue(Resource.success(it))
            }
        }
    }

    fun addToCart(cartItem: CartItem) {
        viewModelScope.launch {
            dbHelper?.insertSingle(cartItem)?.onCompletion {
                getCartCount()
            }?.collect {

            }
        }

    }

    fun updateItem(id: Int?, quantity: Int?) {
        viewModelScope.launch {
            dbHelper?.updateCart(id, quantity)?.onCompletion {
                getCartCount()
            }?.collect {

            }
        }

    }

    fun deleteItem(prodId: Int?) {
        viewModelScope.launch {
            dbHelper?.deleteItem(prodId)?.onCompletion {
                getCartCount()
            }?.collect {

            }
        }

    }

    fun getCartCount() {
        viewModelScope.launch {
            dbHelper?.getCount()?.catch { e ->
                cartCount.postValue(Resource.error(e.toString(), null))
            }?.collect {
                cartCount.postValue(Resource.success(it))
            }
        }
    }
}