package com.hgokumus.cryptoapp.home.di

import com.hgokumus.cryptoapp.network.CryptoService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeDI {
    companion object {
        val module = module {
            single<CryptoService> {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
                val retrofit = Retrofit.Builder()
                    .baseUrl("")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofit.create(CryptoService::class.java)
            }
        }
    }
}