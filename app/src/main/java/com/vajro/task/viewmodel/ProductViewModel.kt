package com.vajro.task.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vajro.task.data.network.ApiHelper
import com.vajro.task.model.ProductResponseDTO
import com.vajro.task.utils.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
class ProductViewModel(private val apiHelper: ApiHelper) : ViewModel() {
    private val products: MutableLiveData<Resource<ProductResponseDTO>> by lazy {
        MutableLiveData<Resource<ProductResponseDTO>>()
    }
    val getProducts: LiveData<Resource<ProductResponseDTO>> get() = products

    init {
        fetchProducts()
    }

     private fun fetchProducts() {
         Log.i("ProductViewMod","")
        viewModelScope.launch {
            products.postValue(Resource.loading(null))
            apiHelper.getProducts().catch { e ->
                products.postValue(Resource.error(e.toString(), null))
            }.collect {
                products.postValue(Resource.success(it))
            }
        }
    }
}