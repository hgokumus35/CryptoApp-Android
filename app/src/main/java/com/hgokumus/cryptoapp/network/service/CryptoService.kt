package com.hgokumus.cryptoapp.network.service

import com.hgokumus.cryptoapp.network.response.CryptoDetailResponse
import com.hgokumus.cryptoapp.network.response.CryptoListResponse
import com.hgokumus.cryptoapp.network.response.PriceHistoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoService {

    @GET("/v2/coins")
    suspend fun getCryptoList(
        @Header("x-access-token") apiKey: String,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("orderBy") orderBy: String? = null,
    ) : Response<CryptoListResponse>

    @GET("/v2/coin/{uuid}")
    suspend fun getCryptoDetail(
        @Header("x-access-token") apiKey: String,
        @Path("uuid") uuid: String
    ) : Response<CryptoDetailResponse>

    @GET("/v2/coin/{uuid}/history")
    suspend fun getPriceHistory(
        @Header("x-access-token") apiKey: String,
        @Path("uuid") uuid: String
    ) : Response<PriceHistoryResponse>
}