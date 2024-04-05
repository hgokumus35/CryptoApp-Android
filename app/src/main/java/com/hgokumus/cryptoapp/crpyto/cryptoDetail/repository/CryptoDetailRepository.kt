package com.hgokumus.cryptoapp.crpyto.cryptoDetail.repository

import com.hgokumus.cryptoapp.network.request.CryptoDetailRequest
import com.hgokumus.cryptoapp.network.request.PriceHistoryRequest
import com.hgokumus.cryptoapp.network.response.CryptoDetailResponse
import com.hgokumus.cryptoapp.network.response.PriceHistoryResponse
import retrofit2.Response

interface CryptoDetailRepository {
    suspend fun getCryptoDetail(request: CryptoDetailRequest) : Response<CryptoDetailResponse>
    suspend fun getPriceHistory(request: PriceHistoryRequest) : Response<PriceHistoryResponse>
}