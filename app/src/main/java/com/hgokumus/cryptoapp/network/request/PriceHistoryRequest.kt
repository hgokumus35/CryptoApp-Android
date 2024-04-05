package com.hgokumus.cryptoapp.network.request

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class PriceHistoryRequest(
    val uuid: String
) : Parcelable