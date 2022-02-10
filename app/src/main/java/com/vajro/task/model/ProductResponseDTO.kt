package com.vajro.task.model
/**
 * Created by gowtham.ashok on 2/9/2022.
 */
data class ProductResponseDTO(
	val products: List<ProductsItem?>? = null
)

data class ProductsItem(
	val special: String? = null,
	val image: String? = null,
	val quantity: Int? = null,
	val thumb: String? = null,
	val price: String? = null,
	val product_id: String? = null,
	val name: String? = null,
	val description: String? = null,
	val id: String? = null,
	val href: String? = null,
	val sku: String? = null,
	val zoomThumb: String? = null,
	var addedQuantity: Int = 0
)

