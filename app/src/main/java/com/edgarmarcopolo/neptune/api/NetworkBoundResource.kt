package com.edgarmarcopolo.neptune.api

import com.edgarmarcopolo.neptune.BuildConfig
import com.edgarmarcopolo.neptune.orEmpty
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class NetworkBoundResource<DomainType, DiskType, CloudType> {

    val run: Flow<Resource<DomainType>> = flow {
        emit(Resource.Loading(getLoadingObject()))
        if (shouldLoad()) {
            val dbSource = loadFromDb()
            if (shouldFetch(dbSource?.first())) {
                emitAll(
                    processResponse(
                        dbSource,
                        getNetworkResource { createCall() }
                    )
                )
            } else {
                emitAll(processResponse(dbSource))
            }
        } else {
            emitAll(processResponse(cloudSource = getNetworkResource { createCall() }))
        }
    }

    protected open suspend fun processResponse(
        diskSource: Flow<DiskType>? = null,
        cloudSource: NetworkResource<CloudType?>? = null
    ): Flow<Resource<DomainType>> = flow {
        when (cloudSource) {
            is NetworkResource.Success -> {
                diskSource?.let { disk ->
                    saveCallResult(convertCloudToDomain(cloudSource.data))
                    emitAll(disk.map { Resource.Success(convertDiskToDomain(it)) })
                } ?: run {
                    emit(Resource.Success(convertCloudToDomain(cloudSource.data)))
                }
            }
            else -> {
                diskSource?.takeIf { cloudSource == null }?.run {
                    emitAll(map { Resource.Success(convertDiskToDomain(it)) })
                } ?: run {
                    emit(
                        Resource.Error(
                            data = cloudSource?.data?.let { convertCloudToDomain(it) }
                                ?: convertDiskToDomain(diskSource?.first()),
                            message = cloudSource?.message.orEmpty(),
                            errorCode = cloudSource?.errorCode.orEmpty()
                        )
                    )
                }
            }
        }
    }

    protected abstract fun convertCloudToDomain(cloudType: CloudType?): DomainType

    protected abstract fun convertDiskToDomain(diskType: DiskType?): DomainType

    protected open suspend fun saveCallResult(item: DomainType) {}

    protected open fun shouldFetch(data: DiskType?): Boolean = true

    protected open fun shouldLoad(): Boolean = true

    protected open suspend fun loadFromDb(): Flow<DiskType>? = null

    protected abstract suspend fun createCall(): Response<CloudType>

    protected open suspend fun getLoadingObject(): DomainType =
        convertDiskToDomain(loadFromDb()?.first())
}

suspend fun <R> getNetworkResource(call: suspend () -> Response<R>): NetworkResource<R?> =
    try {
        val response = call()
        when {
            // Handle Success
            response.isSuccessful -> NetworkResource.Success(response.body())
            // Handle Server Error
            else ->
                NetworkResource.Error(response.body(), response.message(), response.code())
        }
    } catch (e: Exception) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        }
        NetworkResource.Exception(HttpCodes.EXCEPTION_RESPONSE_CODE, e.message.orEmpty())
    }

suspend fun <R> getResource(call: suspend () -> Response<R>): Resource<R?> =
    try {
        val response = call()
        when {
            // Handle Success
            response.isSuccessful -> Resource.Success(response.body())
            // Handle Server Error
            else ->
                Resource.Error(response.body(), response.message(), response.code())
        }
    } catch (e: Exception) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        }
        Resource.Error(
            errorCode = HttpCodes.EXCEPTION_RESPONSE_CODE, data = null,
            message = e.message.orEmpty()
        )
    }