package com.hgokumus.cryptoapp.crpyto.cryptoDetail.di

import com.hgokumus.cryptoapp.crpyto.cryptoDetail.repository.CryptoDetailRepository
import com.hgokumus.cryptoapp.crpyto.cryptoDetail.repository.CryptoDetailRepositoryImpl
import com.hgokumus.cryptoapp.crpyto.cryptoDetail.viewmodel.CryptoDetailViewModel
import com.hgokumus.cryptoapp.network.service.CryptoService
import com.hgokumus.cryptoapp.room.CryptoDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class CryptoDetailDI {
    companion object {
        val module = module {
            viewModel { CryptoDetailViewModel(provideCryptoDetailRepository(get(), get())) }
            single<CryptoDetailRepository> { CryptoDetailRepositoryImpl(get(), get()) }
        }
    }
}

fun provideCryptoDetailRepository(
    cryptoService: CryptoService,
    cryptoDatabase: CryptoDatabase
) : CryptoDetailRepository = CryptoDetailRepositoryImpl(cryptoService, cryptoDatabase)