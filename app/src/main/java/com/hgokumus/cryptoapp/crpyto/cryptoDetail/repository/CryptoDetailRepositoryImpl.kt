package com.hgokumus.cryptoapp.crpyto.cryptoDetail.repository

import com.hgokumus.cryptoapp.core.extensions.Constants.API_KEY
import com.hgokumus.cryptoapp.network.request.CryptoDetailRequest
import com.hgokumus.cryptoapp.network.request.PriceHistoryRequest
import com.hgokumus.cryptoapp.network.response.CryptoDetailResponse
import com.hgokumus.cryptoapp.network.response.PriceHistoryResponse
import com.hgokumus.cryptoapp.network.service.CryptoService
import retrofit2.Response

class CryptoDetailRepositoryImpl(
    private val cryptoService: CryptoService
) : CryptoDetailRepository {
    override suspend fun getCryptoDetail(request: CryptoDetailRequest): Response<CryptoDetailResponse> = cryptoService.getCryptoDetail(
        apiKey = API_KEY,
        uuid = request.uuid
    )

    override suspend fun getPriceHistory(request: PriceHistoryRequest): Response<PriceHistoryResponse> = cryptoService.getPriceHistory(
        apiKey = API_KEY,
        uuid = request.uuid
    )
}