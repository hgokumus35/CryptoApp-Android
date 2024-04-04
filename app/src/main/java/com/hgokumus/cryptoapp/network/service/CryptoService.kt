package com.hgokumus.cryptoapp.network.service

import com.hgokumus.cryptoapp.network.response.CryptoListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CryptoService {

    @GET("/v2/coins")
    suspend fun getCryptoList(
        @Header("x-access-token") token: String,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("orderBy") orderBy: String? = null,
    ) : Response<CryptoListResponse>
}