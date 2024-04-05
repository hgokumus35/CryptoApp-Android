package com.hgokumus.cryptoapp.crpyto.cryptoList.repository

import com.hgokumus.cryptoapp.network.response.CryptoListResponse
import retrofit2.Response

interface CryptoListRepository {
    suspend fun getCryptoList(): Response<CryptoListResponse>
}