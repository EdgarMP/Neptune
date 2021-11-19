package com.edgarmarcopolo.neptune.application.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Marks an activity / fragment injectable.
 */
interface Injectable

@MustBeDocumented
@Retention
@Target(AnnotationTarget.FUNCTION)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
