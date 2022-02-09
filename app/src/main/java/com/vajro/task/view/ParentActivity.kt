package com.vajro.task.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.vajro.task.R

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
class ParentActivity : AppCompatActivity() {
    private var menuItem: Menu? = null
    private lateinit var navController:NavController
//    private lateinit var viewModel: ProductViewModel
//
    private var countView: TextView?=null
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_item, menu)
//        countView = menu?.findItem(R.id.filter)?.actionView as TextView
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
         navController = navHostFragment.navController
        supportActionBar?.hide()
//        countView?.let {
//            it.setOnClickListener { Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()  }
//        }
//        countView.
//        lifecycleScope.launch {
//            delay(2000)
//
//        }

//        supportActionBar?.title = "Product"
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        viewModel = ViewModelProviders.of(
//            this,
//            ViewModelFactory(
//                ApiRepository(
//                    RetrofitBuilder.apiService,
//                    Dispatchers.IO
//                )
//            )
//        )[ProductViewModel::class.java]
//
//        findViewById<TextView>(R.id.tv).setOnClickListener {
//            viewModel.fetchProducts()
//            viewModel.getProducts.observe(this) {
//                when (it.status) {
//                    Status.LOADING -> {
//
//                    }
//                    Status.SUCCESS -> {
//                        Toast.makeText(this, "Sucess", Toast.LENGTH_SHORT).show()
//                    }
//                    Status.ERROR -> {
//                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
//
//                    }
//                }
//            }
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
            R.id.cart->{


                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun setActionBarView(title: String,isBackRequired:Boolean) {
        supportActionBar?.show()
        supportActionBar?.title = title
        if (isBackRequired) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }else{
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }
}