package com.hgokumus.cryptoapp.crpyto.cryptoList.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hgokumus.cryptoapp.core.extensions.Constants.ZERO_INT
import com.hgokumus.cryptoapp.core.extensions.FilterTypesEnum.MARKET_CAP
import com.hgokumus.cryptoapp.core.extensions.FilterTypesEnum.PRICE
import com.hgokumus.cryptoapp.core.extensions.FilterTypesEnum.DAILY_VOLUME
import com.hgokumus.cryptoapp.core.extensions.FilterTypesEnum.CHANGE
import com.hgokumus.cryptoapp.core.extensions.FilterTypesEnum.LISTED_AT
import com.hgokumus.cryptoapp.core.extensions.isNull
import com.hgokumus.cryptoapp.core.extensions.orElse
import com.hgokumus.cryptoapp.crpyto.cryptoList.repository.CryptoListRepository
import com.hgokumus.cryptoapp.network.response.CryptoListResponse
import com.hgokumus.cryptoapp.core.utils.Resource
import com.hgokumus.cryptoapp.network.response.Crypto
import com.hgokumus.cryptoapp.room.entity.FavoriteCryptoEntity
import kotlinx.coroutines.launch

class CryptoListViewModel(
    private val repository: CryptoListRepository
) : ViewModel() {

    private val _getCryptoListEvent = MutableLiveData<Resource<CryptoListResponse>>()
    val getCryptoListEvent: LiveData<Resource<CryptoListResponse>> = _getCryptoListEvent

    private val _getAllFavoritesEvent = MutableLiveData<List<Crypto>>()
    val getAllFavoritesEvent: LiveData<List<Crypto>> = _getAllFavoritesEvent

    var cryptoListResponse: CryptoListResponse? = null
    var currentPage = 0

    val filterTypes = arrayOf(
        MARKET_CAP.orderBy,
        PRICE.orderBy,
        DAILY_VOLUME.orderBy,
        CHANGE.orderBy,
        LISTED_AT.orderBy
    )

    fun getCryptoList(orderBy: String = MARKET_CAP.orderBy, offset: Int = ZERO_INT) = viewModelScope.launch {
        val response = repository.getCryptoList(orderBy, offset)
        response.body()?.let { responseBody ->
            if (response.isSuccessful) {
                currentPage++
                if (cryptoListResponse.isNull() || offset == ZERO_INT) {
                    cryptoListResponse = null
                    cryptoListResponse = responseBody
                } else {
                    val oldCryptoList = cryptoListResponse?.data?.coins
                    val newCryptoList = responseBody.data.coins
                    oldCryptoList?.addAll(newCryptoList)
                }
                _getCryptoListEvent.value = Resource.Success(cryptoListResponse.orElse { responseBody })
            } else {
                _getCryptoListEvent.value = Resource.Error(response.message())
            }
        }
    }

    fun getAllCryptoWithFavorites(cryptoList: List<Crypto>) = viewModelScope.launch {
        val response = repository.getAllFavorites()
        _getAllFavoritesEvent.value = setCryptoListUIModel(cryptoList, response)
    }

    private fun setCryptoListUIModel(cryptoList: List<Crypto>, favoritesList: List<FavoriteCryptoEntity>) : List<Crypto> {
        val updatedList = cryptoList.toMutableList()
        updatedList.forEach { crypto ->
            crypto.isFavorite = favoritesList.any { it.name == crypto.name }
            crypto.id = favoritesList.find { it.name == crypto.name }?.id.orElse { ZERO_INT.toLong() }
        }
        return updatedList
    }
}