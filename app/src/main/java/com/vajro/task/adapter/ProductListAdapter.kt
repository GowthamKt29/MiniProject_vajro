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
    private val productItem: List<ProductsItem?>?,
    private val onAddClick:(Int,ProductsItem)->Unit,
    private val onIncClick:(Int,Int?)->Unit,
    private val onDecClick:(Int,Int?)->Unit
) :
    RecyclerView.Adapter<ProductListAdapter.ProductListHolder>() {


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
                  if (addedQuantity>0){
                      txtAdd.visibility=View.GONE
                        lnrAddQuantity.visibility=View.VISIBLE
                      txtQuantity.text=addedQuantity.toString()
                  }else{
                      txtAdd.visibility=View.VISIBLE
                      lnrAddQuantity.visibility=View.GONE
                  }

                    txtAdd.setOnClickListener {
                        onAddClick(absoluteAdapterPosition,item)
                    }
                    btnInc.setOnClickListener {
                        onIncClick(absoluteAdapterPosition,item.product_id?.toInt())
                    }
                    btnDes.setOnClickListener {
                       if ( txtQuantity.text.toString().toInt()>0){
                           onDecClick(absoluteAdapterPosition,item.product_id?.toInt())
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