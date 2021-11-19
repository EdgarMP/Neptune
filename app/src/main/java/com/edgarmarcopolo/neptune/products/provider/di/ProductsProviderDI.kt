package com.edgarmarcopolo.neptune.products.provider.di

import android.app.Application
import com.edgarmarcopolo.neptune.products.provider.repository.local.ProductsDB
import com.edgarmarcopolo.neptune.products.provider.repository.local.dao.ProductsDao
import com.edgarmarcopolo.neptune.products.provider.repository.remote.NeptuneAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ProductsProviderModule {
//    @Provides
//    fun provideProductsAPI(retrofit: Retrofit): NeptuneAPI {
//        return retrofit.create(NeptuneAPI::class.java)
//    }

    @Provides
    fun provideDatabase(app: Application): ProductsDB =
        ProductsDB.create(app)

    @Provides
    fun provideProductDao(database: ProductsDB): ProductsDao {
        return database.productsDao()
    }
}