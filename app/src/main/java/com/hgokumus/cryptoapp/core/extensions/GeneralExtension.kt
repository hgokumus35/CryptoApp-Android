package com.hgokumus.cryptoapp.core.extensions

inline fun <R> R?.orElse(block: () -> R): R { return this ?: block() }
fun Any?.isNull(): Boolean = this == null
fun Any?.isNotNull(): Boolean = this != null