package com.hgokumus.cryptoapp.crpyto.cryptoDetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.hgokumus.cryptoapp.core.extensions.Constants.ZERO_FLOAT
import com.hgokumus.cryptoapp.core.extensions.orElse
import com.hgokumus.cryptoapp.core.utils.Resource
import com.hgokumus.cryptoapp.crpyto.cryptoDetail.repository.CryptoDetailRepository
import com.hgokumus.cryptoapp.network.request.CryptoDetailRequest
import com.hgokumus.cryptoapp.network.request.PriceHistoryRequest
import com.hgokumus.cryptoapp.network.response.CryptoDetail
import com.hgokumus.cryptoapp.network.response.CryptoDetailResponse
import com.hgokumus.cryptoapp.network.response.History
import com.hgokumus.cryptoapp.network.response.PriceHistoryResponse
import com.hgokumus.cryptoapp.room.entity.FavoriteCryptoEntity
import kotlinx.coroutines.launch

class CryptoDetailViewModel(
    private val repository: CryptoDetailRepository
) : ViewModel() {

    private val _getCryptoDetailEvent = MutableLiveData<Resource<CryptoDetailResponse>>()
    val getCryptoDetailEvent: LiveData<Resource<CryptoDetailResponse>> = _getCryptoDetailEvent

    private val _getPriceHistoryEvent = MutableLiveData<Resource<PriceHistoryResponse>>()
    val getPriceHistoryEvent: LiveData<Resource<PriceHistoryResponse>> = _getPriceHistoryEvent

    private val _addToFavoritesEvent = MutableLiveData<Boolean>()
    val addToFavoritesEvent: LiveData<Boolean> = _addToFavoritesEvent

    private val _removeFromFavoritesEvent = MutableLiveData<Boolean>()
    val removeFromFavoritesEvent: LiveData<Boolean> = _removeFromFavoritesEvent

    var cryptoDetailUIVisibility = MutableLiveData(false)
    var addRemoveButtonVisibility = MutableLiveData(false)
    var cryptoDetail: CryptoDetail? = null
    var timestamps: MutableList<Long> = mutableListOf()

    fun setCryptoDetailUIVisibility(visibility: Boolean) {
        cryptoDetailUIVisibility.value = visibility
    }

    fun setAddRemoveButtonVisibility(visibility: Boolean) {
        addRemoveButtonVisibility.value = visibility
    }

    fun getCryptoDetail(request: CryptoDetailRequest) = viewModelScope.launch {
        val response = repository.getCryptoDetail(request)
        response.body()?.let { responseBody ->
            _getCryptoDetailEvent.value = Resource.Success(responseBody)
                .takeIf { response.isSuccessful }
                .orElse { Resource.Error(response.message()) }
        }
    }

    fun getPriceHistory(request: PriceHistoryRequest) = viewModelScope.launch {
        val response = repository.getPriceHistory(request)
        response.body()?.let { responseBody ->
            _getPriceHistoryEvent.value = Resource.Success(responseBody)
                .takeIf { response.isSuccessful }
                .orElse { Resource.Error(response.message()) }
        }
    }

    fun addToFavorites(favoriteCrypto: FavoriteCryptoEntity) = viewModelScope.launch {
        val isSuccessful = repository.addToFavorites(favoriteCrypto)
        _addToFavoritesEvent.value = isSuccessful
    }

    fun removeFromFavorites(favoriteCrypto: FavoriteCryptoEntity) = viewModelScope.launch {
        val isSuccessful = repository.removeFromFavorites(favoriteCrypto)
        _removeFromFavoritesEvent.value = isSuccessful
    }

    fun cryptoChartUIModel(priceHistoryList: List<History>) : MutableList<Entry> {
        val chartEntries = mutableListOf<Entry>()
        var x = ZERO_FLOAT
        priceHistoryList.reversed().forEach { history ->
            history.timestamp?.let { timestamp ->
                timestamps.add(timestamp)
                val price = history.price?.toFloat().orElse { return@forEach }
                chartEntries.add(Entry(x++, price))
            }
        }
        return chartEntries
    }
}