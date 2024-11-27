package com.my.dailycashflow.util

import androidx.sqlite.db.SimpleSQLiteQuery

object FilterUtils {

    fun getFilterQuery(filter: CashFlowFilterType): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT a.* FROM cashflow a join category b on a.category_id = b.id ")
        when (filter) {
            CashFlowFilterType.EXPENSE -> {
                simpleQuery.append("where b.type = '$EXPENSE' ")
            }
            CashFlowFilterType.INCOME -> {
                simpleQuery.append("where b.type = '$INCOME' ")
            }
        }
        simpleQuery.append("ORDER BY a.dateInMillis DESC, a.id DESC")
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

}