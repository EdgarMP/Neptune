package com.edgarmarcopolo.neptune.api

sealed class Resource<out T> {
    abstract val data: T

    data class Success<out R>(
        override val data: R,
        val message: String = "",
        val stringRes: Int = 0
    ) :
        Resource<R>()

    data class Loading<out R>(override val data: R) : Resource<R>()
    data class Error<out E>(
        override val data: E,
        val message: String = "",
        val errorCode: Int,
        val stringRes: Int = 0
    ) :
        Resource<E>()
}

sealed class NetworkResource<out T> {
    abstract val data: T?
    abstract val message: String
    abstract val errorCode: Int

    data class Success<out R>(
        override val data: R,
        override val errorCode: Int = 0,
        override val message: String = ""
    ) : NetworkResource<R>()

    data class Error<out E>(
        override val data: E?,
        override var message: String,
        override var errorCode: Int
    ) : NetworkResource<E>()

    data class Exception<out E>(
        override var errorCode: Int,
        override val message: String = "",
        override val data: E? = null
    ) : NetworkResource<E>()
}

inline fun <reified T> Resource<T>.doIfError(
    callback: (data: T?, error: String?, errorCode: Int?) ->
    Unit
): Resource<T> = this.apply {
    if (this is Resource.Error) {
        callback(this.data, this.message, this.errorCode)
    }
}

inline fun <reified T> Resource<T>.doIfSuccess(callback: (data: T) -> Unit): Resource<T> =
    this.apply {
        if (this is Resource.Success) {
            callback(this.data)
        }
    }

inline fun <reified T> Resource<T>.doIfLoading(callback: (data: T?) -> Unit): Resource<T> =
    this.apply {
        if (this is Resource.Loading) {
            callback(this.data)
        }
    }