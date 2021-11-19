package com.edgarmarcopolo.neptune.application.di


import com.edgarmarcopolo.neptune.MainActivity
import com.edgarmarcopolo.neptune.MainActivityModule
import com.edgarmarcopolo.neptune.MainActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Module
internal abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    @MainActivityScope
    abstract fun contributeMainActivity(): MainActivity
}