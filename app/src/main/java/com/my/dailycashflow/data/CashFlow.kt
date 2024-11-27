package com.my.dailycashflow.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cashflow")
data class CashFlow(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "information")
    val information: String? = null,

    @ColumnInfo(name = "dateInMillis")
    val dateInMillis: Long? = null,

    @ColumnInfo(name = "category_id")
    val categoryId: Int? = null,

    @ColumnInfo(name = "amount")
    val amount: Int? = null,
)