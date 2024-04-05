package com.hgokumus.cryptoapp.crpyto.cryptoDetail.di

import com.hgokumus.cryptoapp.crpyto.cryptoDetail.repository.CryptoDetailRepository
import com.hgokumus.cryptoapp.crpyto.cryptoDetail.repository.CryptoDetailRepositoryImpl
import com.hgokumus.cryptoapp.crpyto.cryptoDetail.viewmodel.CryptoDetailViewModel
import com.hgokumus.cryptoapp.network.service.CryptoService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class CryptoDetailDI {
    companion object {
        val module = module {
            viewModel { CryptoDetailViewModel(provideCryptoDetailRepository(get())) }
            single<CryptoDetailRepository> { CryptoDetailRepositoryImpl(get()) }
        }
    }
}

fun provideCryptoDetailRepository(cryptoService: CryptoService) : CryptoDetailRepository = CryptoDetailRepositoryImpl(cryptoService)