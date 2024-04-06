package com.hgokumus.cryptoapp.crpyto.cryptoList.repository

import com.hgokumus.cryptoapp.core.extensions.Constants.API_KEY
import com.hgokumus.cryptoapp.network.response.CryptoListResponse
import com.hgokumus.cryptoapp.network.service.CryptoService
import retrofit2.Response

class CryptoListRepositoryImpl(
    private val cryptoService: CryptoService
) : CryptoListRepository {

    override suspend fun getCryptoList(orderBy: String): Response<CryptoListResponse> = cryptoService.getCryptoList(
        apiKey = API_KEY,
        orderBy = orderBy
    )
}