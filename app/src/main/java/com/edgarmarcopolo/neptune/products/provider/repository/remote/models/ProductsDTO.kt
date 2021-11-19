package com.edgarmarcopolo.neptune.products.provider.repository.remote.models

import com.google.gson.annotations.SerializedName

data class ProductsListDTO(
    @SerializedName("batch_id")
    val batchId: Int? = null,
    @SerializedName("offers")
    val products: List<ProductDTO>? = null,
)

data class ProductDTO(
    @SerializedName("offer_id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("cash_back")
    val cashback: Double? = null
)
