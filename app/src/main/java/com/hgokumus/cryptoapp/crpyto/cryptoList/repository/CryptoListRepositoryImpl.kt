package com.hgokumus.cryptoapp.crpyto.cryptoList.repository

import com.hgokumus.cryptoapp.core.extensions.Constants.API_KEY
import com.hgokumus.cryptoapp.network.response.CryptoListResponse
import com.hgokumus.cryptoapp.network.service.CryptoService
import com.hgokumus.cryptoapp.room.CryptoDatabase
import com.hgokumus.cryptoapp.room.entity.FavoriteCryptoEntity
import retrofit2.Response

class CryptoListRepositoryImpl(
    private val cryptoService: CryptoService,
    private val cryptoDatabase: CryptoDatabase
) : CryptoListRepository {
    override suspend fun getAllFavorites(): List<FavoriteCryptoEntity> = cryptoDatabase.cryptoDao().getAllFavorites()

    override suspend fun getCryptoList(orderBy: String): Response<CryptoListResponse> = cryptoService.getCryptoList(
        apiKey = API_KEY,
        orderBy = orderBy
    )
}