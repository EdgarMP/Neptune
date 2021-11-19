package com.edgarmarcopolo.neptune.products.ui.models

data class ProductsUIList(
    val productsUIList: List<ProductUI> = listOf()
)

data class ProductUI(
    val id: Int? = 0,
    val name: String? = "",
    val imageUrl: String? = "",
    val cashbackValue : Double? = 0.0,
    val cashback : String? = ""
)
