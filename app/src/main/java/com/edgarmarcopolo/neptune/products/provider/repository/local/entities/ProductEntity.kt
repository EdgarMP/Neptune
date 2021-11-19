package com.edgarmarcopolo.neptune.products.provider.repository.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index(value = ["offer_id"])])
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "offer_id") val offerId: Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "image_url") val imageUrl: String = "",
    @ColumnInfo(name = "cash_back") val cashback: Double = 0.0

)