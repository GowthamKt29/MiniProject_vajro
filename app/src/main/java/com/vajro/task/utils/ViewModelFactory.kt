package com.vajro.task.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vajro.task.data.local.DatabaseHelper
import com.vajro.task.data.network.ApiHelper
import com.vajro.task.viewmodel.CartViewModel
import com.vajro.task.viewmodel.ProductViewModel

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
class ViewModelFactory(private val apiHelper: ApiHelper?,private val dbHelper: DatabaseHelper?) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(apiHelper,dbHelper) as T
        }
        if (modelClass.isAssignableFrom(CartViewModel::class.java)){
                return CartViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}