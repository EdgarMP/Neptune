package com.edgarmarcopolo.neptune.products.ui

import com.edgarmarcopolo.neptune.getCashFormat
import com.edgarmarcopolo.neptune.products.provider.domain.models.Product
import com.edgarmarcopolo.neptune.products.provider.domain.models.ProductsList
import com.edgarmarcopolo.neptune.products.ui.models.ProductUI
import com.edgarmarcopolo.neptune.products.ui.models.ProductsUIList
import javax.inject.Inject

class ProductsUIMapper @Inject constructor() {

    fun convert(response: ProductsList?): ProductsUIList {
        val list = mutableListOf<ProductUI>().apply {
            response?.productsList?.map {
                this.add(convert(it))
            }
        }
        return ProductsUIList(list)
    }

    private fun convert(response: Product): ProductUI =
        ProductUI(
            id = response.id,
            name = response.name,
            imageUrl = response.imageUrl,
            cashbackValue = response.cashback,
            cashback = response.cashback.getCashFormat()
        )
}