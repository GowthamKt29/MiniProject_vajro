package com.vajro.task.view

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.vajro.task.R
import com.vajro.task.adapter.ProductListAdapter
import com.vajro.task.data.local.DatabaseBuilder
import com.vajro.task.data.local.DatabaseRepoository
import com.vajro.task.data.local.entity.CartItem
import com.vajro.task.data.network.ApiRepository
import com.vajro.task.data.network.RetrofitBuilder
import com.vajro.task.databinding.FragmentProductBinding
import com.vajro.task.model.ProductResponseDTO
import com.vajro.task.model.ProductsItem
import com.vajro.task.utils.Status
import com.vajro.task.utils.ViewModelFactory
import com.vajro.task.viewmodel.ProductViewModel
import kotlinx.coroutines.Dispatchers

/**
 * Created by gowtham.ashok on 2/9/2022.
 */
class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private var countView: TextView? = null
    private lateinit var viewModel: ProductViewModel
    private var productListAdapter: ProductListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        onObServeProduct()
        onObserveCount()
    }

    private fun onObserveCount() {
        viewModel.getCount.observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    countView?.text = it.data.toString()
                }
                Status.ERROR -> {

                }
            }
        }
    }

    private fun onObServeProduct() {
        viewModel.getProducts.observe(requireActivity()) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    setAdapter(it.data)
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE

                }
            }
        }
    }

    private fun setAdapter(data: ProductResponseDTO?) {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(
                context,
                2,
                GridLayoutManager.VERTICAL,
                false
            )
            productListAdapter =
                ProductListAdapter(
                    requireContext().applicationContext,
                    data?.products, { position, item ->
                        data?.products?.get(position)?.addedQuantity = 1
                        productListAdapter?.notifyItemChanged(position)
                        addProductToCart(item)

                    }, { pos, productId ->
                        data?.products?.get(pos)?.addedQuantity =
                            data?.products?.get(pos)?.addedQuantity?.inc()!!
                        productListAdapter?.notifyItemChanged(pos)
                        updateProductItem(productId, data.products[pos]?.addedQuantity)
                    },{posD, productId ->
                        data?.products?.get(posD)?.addedQuantity =
                            data?.products?.get(posD)?.addedQuantity?.dec()!!
                        productListAdapter?.notifyItemChanged(posD)
                        if ( data.products[posD]?.addedQuantity==0){
                            deleteProduct(productId)
                            return@ProductListAdapter
                        }
                        updateProductItem(productId, data.products[posD]?.addedQuantity)
                    })
            adapter = productListAdapter
        }
    }

    private fun deleteProduct(productId: Int?) {
        viewModel.deleteItem(productId)
    }

    private fun updateProductItem(productId: Int?, addedQuantity: Int?) {
        viewModel.updateItem(productId, addedQuantity)
    }

    private fun addProductToCart(item: ProductsItem) {

        viewModel.addToCart(
            CartItem(
                productId = item.product_id?.toInt(),
                name = item.name,
                image = item.image,
                price = item.price,
                quantity = item.addedQuantity
            )
        )
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiRepository(
                    RetrofitBuilder.apiService,
                    Dispatchers.IO
                ),
                DatabaseRepoository(
                    DatabaseBuilder.getInstance(requireContext().applicationContext),
                    Dispatchers.IO
                )
            )
        )[ProductViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as ParentActivity).setActionBarView(
            getString(R.string.lblProcuctTitle),
            false
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        countView = menu.findItem(R.id.cart)?.actionView as TextView
        viewModel.getCartCount()
        countView?.setOnClickListener { findNavController().navigate(R.id.navigate_to_cartFragment) }
        return super.onCreateOptionsMenu(menu, inflater)
    }
}