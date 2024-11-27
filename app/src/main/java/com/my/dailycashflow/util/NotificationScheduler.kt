package com.my.dailycashflow.util

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.my.dailycashflow.notification.NotificationWorker

object NotificationScheduler {

    // Fungsi untuk menjadwalkan notifikasi
    fun scheduleNotifications(context: Context) {
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>().build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    // Fungsi untuk membatalkan semua notifikasi
    fun cancelNotifications(context: Context) {
        WorkManager.getInstance(context).cancelAllWork()
    }
}
