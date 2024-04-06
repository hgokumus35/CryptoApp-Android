package com.hgokumus.cryptoapp.network.response

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CryptoDetailResponse(
    val data: CryptoDetailData
) : Parcelable

@Keep
@Parcelize
data class CryptoDetailData(
    val coin: CryptoDetail
) : Parcelable

@Keep
@Parcelize
data class CryptoDetail(
    val uuid: String?,
    val symbol: String?,
    val name: String?,
    val iconUrl: String?,
    val price: String?,
    val change: String?,
    val rank: Int?,
    val sparkline: List<String>?
) : Parcelable