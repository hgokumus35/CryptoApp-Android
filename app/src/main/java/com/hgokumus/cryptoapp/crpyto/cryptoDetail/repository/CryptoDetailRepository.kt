package com.hgokumus.cryptoapp.crpyto.cryptoDetail.repository

import com.hgokumus.cryptoapp.network.request.CryptoDetailRequest
import com.hgokumus.cryptoapp.network.request.PriceHistoryRequest
import com.hgokumus.cryptoapp.network.response.CryptoDetailResponse
import com.hgokumus.cryptoapp.network.response.PriceHistoryResponse
import com.hgokumus.cryptoapp.room.entity.FavoriteCryptoEntity
import retrofit2.Response

interface CryptoDetailRepository {
    suspend fun getCryptoDetail(request: CryptoDetailRequest) : Response<CryptoDetailResponse>
    suspend fun getPriceHistory(request: PriceHistoryRequest) : Response<PriceHistoryResponse>
    suspend fun addToFavorites(favoriteCrypto: FavoriteCryptoEntity) : Boolean
    suspend fun removeFromFavorites(favoriteCrypto: FavoriteCryptoEntity) : Boolean
}