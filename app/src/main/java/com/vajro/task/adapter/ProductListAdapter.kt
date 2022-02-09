package com.vajro.task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vajro.task.databinding.InflateProductItemBinding
import com.vajro.task.model.ProductsItem


/**
 * Created by gowtham.ashok on 2/10/2022.
 */
class ProductListAdapter(
    private val context: Context,
    private val productItem: List<ProductsItem?>?
) :
    RecyclerView.Adapter<ProductListAdapter.ProductListHolder>() {

//    fun refreshList(merchantList: Array<MerchantListResponseItem>) {
//        this.merchantList = merchantList
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListHolder {
        val itemBinding = InflateProductItemBinding.inflate(
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

  inner  class ProductListHolder(private val itemBinding: InflateProductItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: ProductsItem?) {
            itemBinding.apply {
                item?.apply {
                    txtAdd.setOnClickListener {
                        txtAdd.visibility=View.GONE
                        lnrAddQuantity.visibility=View.VISIBLE
                    }
                    btnInc.setOnClickListener {
                        txtQuantity.text=(txtQuantity.text.toString().toInt()+1).toString()
                    }
                    btnDes.setOnClickListener {
                       if ( txtQuantity.text.toString().toInt()>0){
                           txtQuantity.text=(txtQuantity.text.toString().toInt()-1).toString()
                       }

                    }
                    txtName.text=name
                    txtPrice.text=price
                    Glide.with(context)
                        .load(image)
                        .into(imgProduct)
                }
            }
        }

    }
}