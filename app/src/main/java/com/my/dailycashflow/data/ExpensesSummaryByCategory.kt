package com.my.dailycashflow.data

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExpensesSummaryByCategory(
    @Embedded
    val category: Category,
    val total: Int
) : Parcelable