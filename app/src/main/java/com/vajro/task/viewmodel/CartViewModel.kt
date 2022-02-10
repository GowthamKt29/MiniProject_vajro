package com.vajro.task.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vajro.task.data.local.DatabaseHelper
import com.vajro.task.data.local.entity.CartItem
import com.vajro.task.data.network.ApiHelper
import com.vajro.task.model.ProductResponseDTO
import com.vajro.task.utils.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

/**
 * Created by gowtham.ashok on 2/10/2022.
 */
class CartViewModel(private val dbHelper: DatabaseHelper?) : ViewModel() {
    private val products: MutableLiveData<Resource<List<CartItem>>> by lazy {
        MutableLiveData<Resource<List<CartItem>>>()
    }
    val getProducts: LiveData<Resource<List<CartItem>>> get() = products

    init {
        fetchCartItems()
    }

    private fun fetchCartItems() {
        viewModelScope.launch {
            products.postValue(Resource.loading(null))
            dbHelper?.getAllProducts()?.catch { e ->
                products.postValue(Resource.error(e.toString(), null))
            }?.collect {
                products.postValue(Resource.success(it))
            }
        }
    }

    fun updateItem(id: Int?, quantity: Int?) {
        viewModelScope.launch {
            dbHelper?.updateCart(id, quantity)?.collect {
            }
        }
    }

    fun deleteItem(prodId: Int?) {
        viewModelScope.launch {
            dbHelper?.deleteItem(prodId)?.collect {
            }
        }
    }
}