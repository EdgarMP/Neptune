package com.edgarmarcopolo.neptune.products.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edgarmarcopolo.neptune.R
import com.edgarmarcopolo.neptune.api.doIfError
import com.edgarmarcopolo.neptune.api.doIfLoading
import com.edgarmarcopolo.neptune.api.doIfSuccess
import com.edgarmarcopolo.neptune.products.provider.repository.remote.ProductsRepository
import com.edgarmarcopolo.neptune.products.ui.models.ProductUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val mapper: ProductsUIMapper
) : ViewModel() {

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _showError: MutableLiveData<Boolean> = MutableLiveData()
    val showError: LiveData<Boolean> = _showError

    private val _productList = MutableLiveData<List<ProductUI>>()
    val productList : LiveData<List<ProductUI>> = _productList

    fun getProducts(){
        viewModelScope.launch(Dispatchers.IO) {

            val request = repository.getProducts()

            request.collect { response ->
                response.doIfLoading {
                    showLoading()
                }.doIfSuccess {
                    val productUIList = mapper.convert(response.data)
                    _productList.postValue(productUIList.productsUIList)
                    hideError()
                    hideProgress()
                }.doIfError { data, error, errorCode ->
                    getProductsOffline()
                }

            }
        }
    }

    private fun getProductsOffline(){
        viewModelScope.launch(Dispatchers.IO) {
            val query = repository.getProductsOffline()

            query.collect { result ->
                result.doIfLoading {
                    showLoading()
                }.doIfSuccess {
                    hideError()
                    hideProgress()
                    val productUIList = mapper.convert(result.data)
                    _productList.postValue(productUIList.productsUIList)
                }.doIfError { data, error, errorCode ->
                    showError()
                }
            }
        }
    }

    fun sortProductsByCashback(){
        val listSorted = _productList.value?.sortedByDescending {
            it.cashbackValue
        }
        _productList.postValue(listSorted)
    }

    private fun showLoading() {
        if(_showLoading.value == false) {
            _showLoading.postValue(true)
        }
        _showError.postValue(false)
    }

    private fun hideProgress(){
        _showLoading.postValue(false)
    }

    private fun showError() {
        if(_showError.value == false) {
            _showError.postValue(true)
        }
        _showLoading.postValue(false)
    }

    private fun hideError() {
        if(_showError.value == true) {
            _showError.postValue(false)
        }
    }

}