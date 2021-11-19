package com.edgarmarcopolo.neptune.products.provider.domain.models

data class ProductsList(
    val batchId: Int? = 0,
    val productsList: List<Product> = listOf()
)

data class Product(
    val id: Int? = 0,
    val name: String? = "",
    val imageUrl: String? = "",
    val cashback : Double = 0.0
)
