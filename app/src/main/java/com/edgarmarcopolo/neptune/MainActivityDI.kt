package com.edgarmarcopolo.neptune

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Scope

@ExperimentalCoroutinesApi
@Module(includes = [MainActivityViewModelModule::class, MainActivityFragmentsBindingModule::class])
class MainActivityModule {

}

@ExperimentalCoroutinesApi
@Module
internal abstract class MainActivityViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(neptuneViewModelFactory: NeptuneViewModelFactory?): ViewModelProvider.Factory?
}

@Scope
@Retention(AnnotationRetention.BINARY)
annotation class MainActivityScope
