package com.hgokumus.cryptoapp.crpyto.cryptoDetail.repository

import com.hgokumus.cryptoapp.core.extensions.Constants.API_KEY
import com.hgokumus.cryptoapp.network.request.CryptoDetailRequest
import com.hgokumus.cryptoapp.network.request.PriceHistoryRequest
import com.hgokumus.cryptoapp.network.response.CryptoDetailResponse
import com.hgokumus.cryptoapp.network.response.PriceHistoryResponse
import com.hgokumus.cryptoapp.network.service.CryptoService
import com.hgokumus.cryptoapp.room.CryptoDatabase
import com.hgokumus.cryptoapp.room.entity.FavoriteCryptoEntity
import retrofit2.Response

class CryptoDetailRepositoryImpl(
    private val cryptoService: CryptoService,
    private val cryptoDatabase: CryptoDatabase
) : CryptoDetailRepository {
    override suspend fun getCryptoDetail(request: CryptoDetailRequest): Response<CryptoDetailResponse> = cryptoService.getCryptoDetail(
        apiKey = API_KEY,
        uuid = request.uuid
    )

    override suspend fun getPriceHistory(request: PriceHistoryRequest): Response<PriceHistoryResponse> = cryptoService.getPriceHistory(
        apiKey = API_KEY,
        uuid = request.uuid
    )

    override suspend fun addToFavorites(favoriteCrypto: FavoriteCryptoEntity): Boolean {
        return try {
            cryptoDatabase.cryptoDao().insert(favoriteCrypto)
            true
        } catch (e: Exception) { false }
    }

    override suspend fun removeFromFavorites(favoriteCrypto: FavoriteCryptoEntity): Boolean {
        return try {
            cryptoDatabase.cryptoDao().delete(favoriteCrypto)
            true
        } catch (e: Exception) { false }
    }
}