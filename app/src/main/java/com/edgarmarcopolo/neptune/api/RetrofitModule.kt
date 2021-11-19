package com.edgarmarcopolo.neptune.api

import com.edgarmarcopolo.neptune.BuildConfig
import com.edgarmarcopolo.neptune.application.di.BaseApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class RetrofitModule {

    @Provides
    @BaseApplicationScope
    fun provideRetrofit(
        @Named(URL_PROPERTY) url: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @BaseApplicationScope
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.callTimeout(0, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(0, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(0, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(0, TimeUnit.SECONDS)

        return okHttpClientBuilder.build()
    }

    @Provides
    @Named(URL_PROPERTY)
    @BaseApplicationScope
    fun providesHttpUrl(): String = BuildConfig.BASE_URL

    @Provides
    @BaseApplicationScope
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    companion object {
        const val URL_PROPERTY = "base_url"
    }
}