package com.vajro.task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vajro.task.data.local.entity.CartItem
import com.vajro.task.databinding.InflateCartItemBinding
import com.vajro.task.databinding.InflateProductItemBinding
import com.vajro.task.model.ProductsItem


/**
 * Created by gowtham.ashok on 2/10/2022.
 */
class CartListAdapter(
    private val context: Context,
    private val productItem: List<CartItem?>?,
    private val onIncClick: (Int, Int?) -> Unit,
    private val onDecClick: (Int, Int?) -> Unit
) :
    RecyclerView.Adapter<CartListAdapter.ProductListHolder>() {

//    fun refreshList(merchantList: Array<MerchantListResponseItem>) {
//        this.merchantList = merchantList
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListHolder {
        val itemBinding = InflateCartItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductListHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ProductListHolder, position: Int) {
        val item = productItem?.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return productItem?.size ?: 0
    }

    inner class ProductListHolder(private val itemBinding: InflateCartItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: CartItem?) {
            itemBinding.apply {
                item?.apply {
                    if (quantity!! > 0) {
                        txtAdd.visibility = View.GONE
                        lnrAddQuantity.visibility = View.VISIBLE
                        txtQuantity.text = quantity.toString()
                    } else {
                        txtAdd.visibility = View.VISIBLE
                        lnrAddQuantity.visibility = View.GONE
                    }
//

                    btnInc.setOnClickListener {
                        onIncClick(absoluteAdapterPosition, item.productId)
                    }
                    btnDes.setOnClickListener {
                       if ( txtQuantity.text.toString().toInt()>1){
                           onDecClick(absoluteAdapterPosition, item.productId)
                       }

                    }
                    val proPrice = price?.filter { it.isDigit() }
                    txtTotalPrice.text = proPrice.toString()
                    txtTotalPrice.text =
                        "₹" + (quantity?.let { proPrice?.toDouble()?.times(it) }).toString()
                    txtName.text = name
                    txtPrice.text = price + "  X  " + quantity
                    Glide.with(context)
                        .load(image)
                        .into(imgProduct)
                }
            }
        }

    }
}