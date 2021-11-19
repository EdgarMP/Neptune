package com.edgarmarcopolo.neptune.products.provider.domain

import com.edgarmarcopolo.neptune.orEmpty
import com.edgarmarcopolo.neptune.products.provider.domain.models.Product
import com.edgarmarcopolo.neptune.products.provider.domain.models.ProductsList
import com.edgarmarcopolo.neptune.products.provider.repository.local.entities.ProductEntity
import com.edgarmarcopolo.neptune.products.provider.repository.remote.models.ProductDTO
import com.edgarmarcopolo.neptune.products.provider.repository.remote.models.ProductsListDTO
import javax.inject.Inject

class ProductsDataMapper @Inject constructor() {

    fun convert(response: ProductsListDTO?): ProductsList {
        val list = mutableListOf<Product>().apply {
            response?.products?.map {
                this.add(convert(it))
            }
        }

        return ProductsList(
            productsList = list,
        )
    }

    private fun convert(response: ProductDTO): Product =
        Product(
            id = response.id ?: 0,
            name = response.name.orEmpty(),
            imageUrl = response.imageUrl.orEmpty(),
            cashback = response.cashback.orEmpty()
        )

    fun convertToEntity(response: ProductDTO): ProductEntity =
        ProductEntity(
            offerId = response.id ?: 0,
            name = response.name.orEmpty(),
            imageUrl = response.imageUrl.orEmpty(),
            cashback = response.cashback.orEmpty()
        )

    private fun convertEntityToDTO(response: ProductEntity): ProductDTO =
        ProductDTO(
            id = response.offerId,
            name = response.name,
            imageUrl = response.imageUrl,
            cashback = response.cashback.orEmpty()
        )

    fun convertEntityToDTOList(response: List<ProductEntity>?) : ProductsListDTO {

        val list = mutableListOf<ProductDTO>().apply {
            response?.map {
                this.add(convertEntityToDTO(it))
            }
        }

        return ProductsListDTO(batchId = 0, products = list)
    }


}