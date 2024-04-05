package com.hgokumus.cryptoapp.crpyto.cryptoList.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hgokumus.cryptoapp.core.extensions.orElse
import com.hgokumus.cryptoapp.crpyto.cryptoList.repository.CryptoListRepository
import com.hgokumus.cryptoapp.network.response.CryptoListResponse
import com.hgokumus.cryptoapp.core.utils.Resource
import kotlinx.coroutines.launch

class CryptoListViewModel(
    private val repository: CryptoListRepository
) : ViewModel() {

    private val _getCryptoListEvent = MutableLiveData<Resource<CryptoListResponse>>()
    val getCryptoListEvent: LiveData<Resource<CryptoListResponse>> = _getCryptoListEvent

    fun getCryptoList() = viewModelScope.launch {
        val response = repository.getCryptoList()
        response.body()?.let { responseBody ->
            _getCryptoListEvent.value = Resource.Success(responseBody)
                .takeIf { response.isSuccessful }
                .orElse { Resource.Error(response.message()) }
        }
    }
}