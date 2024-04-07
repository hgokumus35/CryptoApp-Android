package com.hgokumus.cryptoapp.network.response

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class PriceHistoryResponse(
    val data: PriceHistory
) : Parcelable

@Keep
@Parcelize
data class PriceHistory(
    val change: String?,
    val history: List<History>
) : Parcelable

@Keep
@Parcelize
data class History(
    val price: String?,
    val timestamp: Long?
) : Parcelable