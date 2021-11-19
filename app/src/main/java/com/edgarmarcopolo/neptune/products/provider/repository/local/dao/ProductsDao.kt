package com.edgarmarcopolo.neptune.products.provider.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import retrofit2.Response
import com.edgarmarcopolo.neptune.products.provider.repository.local.entities.ProductEntity

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(entity: ProductEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<ProductEntity>)

    @Query("SELECT * FROM ProductEntity")
    fun getAllData(): List<ProductEntity>

}