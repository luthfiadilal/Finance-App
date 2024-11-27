package com.my.dailycashflow.data

import androidx.room.Embedded
import androidx.room.Relation

data class CashFlowWithCategory(
    @Embedded
    val cashFlow: CashFlow,

    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: Category?
)