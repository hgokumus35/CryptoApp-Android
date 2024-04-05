package com.hgokumus.cryptoapp.crpyto.cryptoList.di

import com.hgokumus.cryptoapp.crpyto.cryptoList.repository.CryptoListRepository
import com.hgokumus.cryptoapp.crpyto.cryptoList.repository.CryptoListRepositoryImpl
import com.hgokumus.cryptoapp.crpyto.cryptoList.viewmodel.CryptoListViewModel
import com.hgokumus.cryptoapp.network.service.CryptoService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class CryptoListDI {
    companion object {
        val module = module {
            viewModel { CryptoListViewModel(provideCryptoListRepository(get())) }
            single<CryptoListRepository> { CryptoListRepositoryImpl(get()) }
        }
    }
}

fun provideCryptoListRepository(cryptoService: CryptoService) : CryptoListRepository = CryptoListRepositoryImpl(cryptoService)