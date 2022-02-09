package com.vajro.task.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vajro.task.R

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
class HomeActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_product)
    }
}