package com.vajro.task.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vajro.task.R
import com.vajro.task.adapter.CartListAdapter
import com.vajro.task.adapter.ProductListAdapter
import com.vajro.task.data.local.DatabaseBuilder
import com.vajro.task.data.local.DatabaseRepoository
import com.vajro.task.data.local.entity.CartItem
import com.vajro.task.databinding.FragmentCartBinding
import com.vajro.task.utils.Status
import com.vajro.task.utils.ViewModelFactory
import com.vajro.task.viewmodel.CartViewModel
import kotlinx.coroutines.Dispatchers

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartViewModel
    private var cartListAdapter: CartListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        onObServeProduct()

    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as ParentActivity).setActionBarView(
            getString(R.string.lblCartTitle),
            true
        )
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                null,
                DatabaseRepoository(
                    DatabaseBuilder.getInstance(requireContext().applicationContext),
                    Dispatchers.IO
                )
            )
        )[CartViewModel::class.java]
    }

    private fun onObServeProduct() {
        viewModel.getProducts.observe(requireActivity()) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtEmptyCart.visibility = View.GONE
                    setTotalPrice(it.data)
                    setAdapter(it.data)
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE

                }
            }
        }
    }

    private fun setTotalPrice(data: List<CartItem>?) {
        val priceList = data?.map { cart ->
            val price = cart.price?.filter { it ->
                it.isDigit()
            }
            cart.quantity?.let { it1 -> price?.toDouble()?.times(it1) }
        }
        binding.txtPrice.text = "Total Price: " + "₹" + priceList.sum().toString()
    }

    private fun setAdapter(data: List<CartItem>?) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            cartListAdapter =
                CartListAdapter(
                    requireContext().applicationContext,
                    data, { pos, productId ->
                        data?.get(pos)?.quantity =
                            data?.get(pos)?.quantity?.inc()!!
                        cartListAdapter?.notifyItemChanged(pos)
                        setTotalPrice(data)
                        updateProductItem(productId, data[pos].quantity)
                    }, { posD, productId ->
                        data?.get(posD)?.quantity =
                            data?.get(posD)?.quantity?.dec()!!
                        cartListAdapter?.notifyItemChanged(posD)
                        setTotalPrice(data)
                        if (data[posD].quantity == 0) {
                            deleteProduct(productId)
                            return@CartListAdapter
                        }
                        updateProductItem(productId, data[posD].quantity)
                    })
            adapter = cartListAdapter
        }
    }

    private fun deleteProduct(productId: Int?) {
        viewModel.deleteItem(productId)
    }

    private fun updateProductItem(productId: Int?, addedQuantity: Int?) {
        viewModel.updateItem(productId, addedQuantity)
    }
}


private fun <E> List<E>?.sum(): Any? {
    var sum = 0.0
    this?.forEach {
        if (it is Double) {
            sum += it
        }
    }
    return sum
}
