package com.vajro.task.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vajro.task.data.network.ApiHelper
import com.vajro.task.viewmodel.ProductViewModel

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
class ViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(apiHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}