package com.vajro.task.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by gowtham.ashok on 2/10/2022.
 */
@Entity
data class CartItem(
    @PrimaryKey val productId: Int?=null,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "quantity") var quantity: Int?,
)