package com.my.dailycashflow.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

const val formatDate = "dd-MMM-yyyy"
val sdf = SimpleDateFormat(formatDate, Locale.US)
var calendar: Calendar = Calendar.getInstance()

fun Long.convertLongToTime(): String {
    calendar.timeInMillis = this
    return sdf.format(calendar.timeInMillis)
}

fun String.convertDateToMillis(): Long {
    val sdf = SimpleDateFormat(formatDate, Locale.US)
    val date = sdf.parse(this)
    return date?.time ?: 0
}

fun Int.formatToIDR(): String {
    val localId = Locale("in", "ID")
    val formatter: NumberFormat = NumberFormat.getCurrencyInstance(localId)
    formatter.maximumFractionDigits = 0
    return formatter.format(this)
}