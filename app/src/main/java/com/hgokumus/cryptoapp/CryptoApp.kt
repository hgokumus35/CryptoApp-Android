package com.hgokumus.cryptoapp

import android.app.Application
import com.hgokumus.cryptoapp.cryptoList.di.CryptoListDI
import com.hgokumus.cryptoapp.home.di.HomeDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CryptoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CryptoApp)
            modules(
                listOf(
                    HomeDI.module,
                    CryptoListDI.module
                )
            )
        }
    }
}