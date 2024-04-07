package com.hgokumus.cryptoapp.crpyto.cryptoList.repository

import com.hgokumus.cryptoapp.network.response.CryptoListResponse
import com.hgokumus.cryptoapp.room.entity.FavoriteCryptoEntity
import retrofit2.Response

interface CryptoListRepository {
    suspend fun getAllFavorites() : List<FavoriteCryptoEntity>
    suspend fun getCryptoList(orderBy: String, offSet: Int): Response<CryptoListResponse>
}