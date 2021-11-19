package com.edgarmarcopolo.neptune.application.di

import android.app.Application
import android.content.Context
import com.edgarmarcopolo.neptune.api.RetrofitModule
import com.edgarmarcopolo.neptune.application.NeptuneApplication
import com.edgarmarcopolo.neptune.products.provider.di.ProductsProviderModule
import com.edgarmarcopolo.neptune.products.provider.repository.remote.NeptuneAPI
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import retrofit2.Retrofit
import javax.inject.Scope

@ExperimentalCoroutinesApi
@FlowPreview
@Component(
    modules = [AndroidSupportInjectionModule::class, ApplicationModule::class]
)
@BaseApplicationScope
interface ApplicationComponent {
    fun inject(neptuneApplication: NeptuneApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Module(
    includes = [
        ActivityBindingModule::class, RetrofitModule::class, ProductsProviderModule::class
    ]
)
internal class ApplicationModule {
    @Provides
    @BaseApplicationScope
    fun provideApplicationAPI(retrofit: Retrofit): NeptuneAPI {
        return retrofit.create(NeptuneAPI::class.java)
    }
}

object ContextInjection {
    fun inject(to: Any, with: Context) {
        (with.applicationContext as HasAndroidInjector).androidInjector().inject(to)
    }
}

@Scope
@Retention(AnnotationRetention.BINARY)
annotation class BaseApplicationScope