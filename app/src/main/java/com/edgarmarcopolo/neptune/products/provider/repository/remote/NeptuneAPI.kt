package com.edgarmarcopolo.neptune.products.provider.repository.remote

import com.edgarmarcopolo.neptune.products.provider.repository.remote.models.ProductsListDTO
import retrofit2.Response
import retrofit2.http.*

interface NeptuneAPI {


    @GET("b/LTL9")
    suspend fun getProducts() : Response<ProductsListDTO>
}