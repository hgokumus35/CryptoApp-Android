package com.hgokumus.cryptoapp.home.di

import androidx.room.Room
import com.hgokumus.cryptoapp.network.service.CryptoService
import com.hgokumus.cryptoapp.room.CryptoDatabase
import com.hgokumus.cryptoapp.room.dao.CryptoDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
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
                    .baseUrl("https://api.coinranking.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofit.create(CryptoService::class.java)
            }
            single {
                Room.databaseBuilder(
                    androidContext(),
                    CryptoDatabase::class.java, "CryptoDatabase"
                ).allowMainThreadQueries().build()
            }
        }
    }
}