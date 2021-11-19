package com.edgarmarcopolo.neptune.products.provider.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.edgarmarcopolo.neptune.products.provider.repository.local.dao.ProductsDao
import com.edgarmarcopolo.neptune.products.provider.repository.local.entities.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)

abstract class ProductsDB : RoomDatabase() {

    abstract fun productsDao(): ProductsDao

    companion object {
        private const val PRODUCTS_DB = "products_db"

        fun create(context: Context): ProductsDB {
            return Room.databaseBuilder(context, ProductsDB::class.java, PRODUCTS_DB)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
