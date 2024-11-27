package com.my.dailycashflow.util

const val INCOME = "Income"
const val EXPENSE = "Expense"
const val CATEGORY_ID = "category_id"
const val CATEGORY_NAME = "category_name"
const val CATEGORY_TOTAL = "category_total"
const val CATEGORY_LIMIT = "category_limit"
const val NOTIFICATION_CHANNEL_ID = "notify_channel"
const val NOTIF_UNIQUE_WORK = "notif_unique_work"
//const val QUERY_EXPENSES_SUMMARY_BY_CATEGORY =
//    "select ca.*, (select sum(ex.amount) from cashflow where category_id = ex.category_id) as total" +
//            "  FROM cashflow ex join category ca on ex.category_id = ca.id WHERE ex.category_id != 1\n" +
//            "GROUP by ex.category_id"

//const val QUERY_EXPENSES_SUMMARY_BY_CATEGORY =
//    "SELECT ca.*, " +
//            "(SELECT SUM(ex.amount) FROM cashflow WHERE category_id = ex.category_id) AS total " +
//            "FROM cashflow ex " +
//            "JOIN category ca ON ex.category_id = ca.id " +
//            "WHERE ca.type = 'Expense' " +
//            "GROUP BY ex.category_id"


const val QUERY_EXPENSES_SUMMARY_BY_CATEGORY =
    "SELECT ca.*, " +
            "(SELECT SUM(ex.amount) FROM cashflow WHERE category_id = ex.category_id) AS total " +
            "FROM cashflow ex " +
            "JOIN category ca ON ex.category_id = ca.id " +
            "WHERE ca.type = 'Expense' " +
            "GROUP BY ex.category_id " +
            "ORDER BY MAX(ex.dateInMillis) DESC"
