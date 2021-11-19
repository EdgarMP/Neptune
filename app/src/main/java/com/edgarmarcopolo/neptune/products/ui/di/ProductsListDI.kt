package com.edgarmarcopolo.neptune.products.ui.di

import androidx.lifecycle.ViewModel
import com.edgarmarcopolo.neptune.application.di.ViewModelKey
import com.edgarmarcopolo.neptune.products.provider.di.ProductsProviderModule
import com.edgarmarcopolo.neptune.products.ui.ProductsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Scope

@ExperimentalCoroutinesApi
@Module(includes = [ProductsViewModelModule::class, ProductsProviderModule::class])
class ProductsFragmentModule

@ExperimentalCoroutinesApi
@Module
abstract class ProductsViewModelModule {
    @Binds
    @IntoMap
    @ProductsFragmentScope
    @ViewModelKey(ProductsViewModel::class)
    abstract fun bindProductsViewModel(productsViewModel: ProductsViewModel): ViewModel
}

@Scope
@Retention(AnnotationRetention.BINARY)
annotation class ProductsFragmentScope