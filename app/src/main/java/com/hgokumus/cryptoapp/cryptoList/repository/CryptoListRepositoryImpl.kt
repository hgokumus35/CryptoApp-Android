package com.hgokumus.cryptoapp.cryptoList.repository

import com.hgokumus.cryptoapp.core.extensions.Constants
import com.hgokumus.cryptoapp.network.response.CryptoListResponse
import com.hgokumus.cryptoapp.network.service.CryptoService
import retrofit2.Response

class CryptoListRepositoryImpl(
    private val cryptoService: CryptoService
) : CryptoListRepository {

    override suspend fun getCryptoList(): Response<CryptoListResponse> = cryptoService.getCryptoList(Constants.API_KEY)
}