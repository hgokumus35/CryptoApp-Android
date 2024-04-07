package com.hgokumus.cryptoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hgokumus.cryptoapp.room.dao.CryptoDao
import com.hgokumus.cryptoapp.room.entity.FavoriteCryptoEntity

@Database(entities = [FavoriteCryptoEntity::class], version = 1)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun cryptoDao() : CryptoDao
}