package com.hgokumus.cryptoapp.cryptoList.di

import com.hgokumus.cryptoapp.cryptoList.repository.CryptoListRepository
import com.hgokumus.cryptoapp.cryptoList.repository.CryptoListRepositoryImpl
import com.hgokumus.cryptoapp.cryptoList.viewmodel.CryptoListViewModel
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