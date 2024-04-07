package com.hgokumus.cryptoapp.network.response

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CryptoListResponse(
    val data: CryptoList
) : Parcelable

@Keep
@Parcelize
data class CryptoList(
    val coins: List<Crypto>
) : Parcelable

@Keep
@Parcelize
data class Crypto(
    val uuid: String?,
    val symbol: String?,
    val name: String?,
    val iconUrl: String?,
    val price: String?,
    val change: String?,
    var isFavorite: Boolean = false
) : Parcelable