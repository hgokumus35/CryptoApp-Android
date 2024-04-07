package com.hgokumus.cryptoapp.room.dao

import androidx.room.*
import com.hgokumus.cryptoapp.room.entity.FavoriteCryptoEntity

@Dao
interface CryptoDao {
    @Query("SELECT * FROM FavoriteCrypto")
    fun getAllFavorites() : List<FavoriteCryptoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteCrypto : FavoriteCryptoEntity)

    @Delete
    fun delete(favoriteCrypto : FavoriteCryptoEntity)
}