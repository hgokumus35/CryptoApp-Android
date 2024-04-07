package com.hgokumus.cryptoapp.core.extensions

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import com.hgokumus.cryptoapp.core.extensions.Constants.CRYPTO_PRICE_FORMAT
import com.hgokumus.cryptoapp.core.extensions.Constants.TIMESTAMP_TO_STRING_FORMAT
import java.text.SimpleDateFormat
import kotlin.math.abs

fun String.formatNumber(pattern: String) : String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("tr", "TR")) as DecimalFormat
    numberFormat.applyPattern(pattern)
    return numberFormat.format(this.toDoubleOrNull().orElse { return this })
}

fun formatChangeAndPrice(change: Double, price: Double): String {
    val result = change * price / 100
    val formattedResult = DecimalFormat(CRYPTO_PRICE_FORMAT).format(abs(result))
    val formattedChange = "+${change.toString().formatNumber(CRYPTO_PRICE_FORMAT)}"
        .takeIf { change >= 0 }.orElse { change.toString().formatNumber(CRYPTO_PRICE_FORMAT) }
    val formattedPrice = "+$$formattedResult"
        .takeIf { result >= 0 }.orElse { "-$$formattedResult" }
    return "$formattedChange%($formattedPrice)"
}

fun timestampToDateString(timestampList: List<Long>): List<String> {
    val convertedList: MutableList<String> = mutableListOf()
    timestampList.forEach { timeStamp ->
        val dateFormat = SimpleDateFormat(TIMESTAMP_TO_STRING_FORMAT, Locale.getDefault())
        val date = Date(timeStamp * 1000)
        convertedList.add(dateFormat.format(date))
    }
    return convertedList
}