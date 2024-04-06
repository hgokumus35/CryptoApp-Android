package com.hgokumus.cryptoapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "FavoriteCrypto")
data class FavoriteCryptoEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "iconUrl") val iconUrl: String?,
    @ColumnInfo(name = "rank") val rank: Int?
)