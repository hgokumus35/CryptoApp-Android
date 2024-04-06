package com.hgokumus.cryptoapp.core.extensions

object Constants {
    const val API_KEY = "coinranking908bd270a914301737a376500fe8983b6070f39242a4a505"
    const val EMPTY_STR = ""
    const val CRYPTO_PRICE_FORMAT = "#,##0.00"
}

enum class FilterTypesEnum(val orderBy: String) {
    MARKET_CAP("marketCap"),
    PRICE("price"),
    CHANGE("change"),
    DAILY_VOLUME("24hVolume"),
    LISTED_AT("listedAt")
}