package com.hgokumus.cryptoapp.core.extensions

object Constants {
    const val API_KEY = "coinranking908bd270a914301737a376500fe8983b6070f39242a4a505"
    const val EMPTY_STR = ""
    const val CRYPTO_PRICE_FORMAT = "#,##0.00"
    const val TIMESTAMP_TO_STRING_FORMAT = "HH:mm:ss"
    const val ZERO_INT = 0
    const val ZERO_DOUBLE = 0.0
    const val ZERO_FLOAT = 1f
    const val TWO_FLOAT = 2f
    const val FIVE_INT = 5
    const val FIFTEEN_INT = 15
}

enum class FilterTypesEnum(val orderBy: String) {
    MARKET_CAP("marketCap"),
    PRICE("price"),
    CHANGE("change"),
    DAILY_VOLUME("24hVolume"),
    LISTED_AT("listedAt")
}