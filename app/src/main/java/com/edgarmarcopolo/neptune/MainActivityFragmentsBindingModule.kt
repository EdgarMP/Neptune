package com.edgarmarcopolo.neptune

import com.edgarmarcopolo.neptune.products.ui.ProductsFragment
import com.edgarmarcopolo.neptune.products.ui.di.ProductsFragmentModule
import com.edgarmarcopolo.neptune.products.ui.di.ProductsFragmentScope
import com.edgarmarcopolo.neptune.products.ui.di.ProductsViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
internal abstract class MainActivityFragmentsBindingModule {

    @ContributesAndroidInjector(modules = [ProductsViewModelModule::class])
    @ProductsFragmentScope
    abstract fun contributeProductFragment(): ProductsFragment?
}