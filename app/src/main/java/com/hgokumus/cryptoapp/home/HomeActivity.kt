package com.hgokumus.cryptoapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hgokumus.cryptoapp.R
import com.hgokumus.cryptoapp.cryptoList.ui.CryptoListFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.homeContainer, CryptoListFragment.newInstance())
                .commitNow()
        }
    }
}