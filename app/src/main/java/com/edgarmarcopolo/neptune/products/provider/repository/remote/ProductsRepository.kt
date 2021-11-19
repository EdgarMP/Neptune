package com.edgarmarcopolo.neptune.products.provider.repository.remote


import com.edgarmarcopolo.neptune.api.Resource
import com.edgarmarcopolo.neptune.api.getResource
import com.edgarmarcopolo.neptune.application.di.BaseApplicationScope
import com.edgarmarcopolo.neptune.orDefault
import com.edgarmarcopolo.neptune.orFalse
import com.edgarmarcopolo.neptune.products.provider.domain.ProductsDataMapper
import com.edgarmarcopolo.neptune.products.provider.domain.models.ProductsList
import com.edgarmarcopolo.neptune.products.provider.repository.local.dao.ProductsDao
import com.edgarmarcopolo.neptune.products.provider.repository.remote.models.ProductDTO
import com.edgarmarcopolo.neptune.products.provider.repository.remote.models.ProductsListDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@BaseApplicationScope
class ProductsRepository @Inject constructor(
    private val api: NeptuneAPI,
    private val mapper: ProductsDataMapper,
    private val dao: ProductsDao?,
) {

    fun getProducts(): Flow<Resource<ProductsList?>> = flow {
        val response = getResource {
            emit(Resource.Loading(null))
            api.getProducts()
        }

        if(response is Resource.Error || response.data?.products?.isEmpty().orFalse()){
            emit(Resource.Error(null, errorCode = 0, message = ""))
        }else{
            insertProducts(response.data?.products.orDefault(listOf()))
            emit(Resource.Success(mapper.convert(response.data.orDefault(ProductsListDTO()))))
        }


    }.flowOn(Dispatchers.IO)

    private suspend fun insertProducts(list : List<ProductDTO>) {
        dao?.insertMessages(
            list.map {
                mapper.convertToEntity(it)
            }
        )
    }

    fun getProductsOffline() : Flow<Resource<ProductsList?>> = flow {
        emit(Resource.Loading(null))
        val products = mapper.convertEntityToDTOList(dao?.getAllData())
        if(products.products.isNullOrEmpty()){
            emit(Resource.Error(null, errorCode = 0, message = ""))
        }else{
            emit(Resource.Success(mapper.convert(products)))
        }

    }

}