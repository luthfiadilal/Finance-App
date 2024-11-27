package com.my.dailycashflow.data

import androidx.room.Embedded
import androidx.room.Relation

data class CashFlowSummaryByCategory(
    @Embedded
    val cashFlow: CashFlow,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: Category?,
    val total: Int
)