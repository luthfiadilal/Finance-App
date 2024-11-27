package com.my.dailycashflow.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.my.dailycashflow.R
import com.my.dailycashflow.util.CATEGORY_ID
import com.my.dailycashflow.util.CATEGORY_LIMIT
import com.my.dailycashflow.util.CATEGORY_NAME
import com.my.dailycashflow.util.CATEGORY_TOTAL
import com.my.dailycashflow.util.NOTIFICATION_CHANNEL_ID

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val categoryId = inputData.getInt(CATEGORY_ID, 0)
    private val categoryName = inputData.getString(CATEGORY_NAME)
    private val categoryTotal = inputData.getInt(CATEGORY_TOTAL, 0)
    private val categoryLimit = inputData.getInt(CATEGORY_LIMIT, 0)

    override fun doWork(): Result {
        val prefManager = androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val shouldNotify = prefManager.getBoolean(applicationContext.getString(R.string.pref_key_notify), false)

        if (shouldNotify && categoryTotal > categoryLimit) {
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Buat Notification Channel (hanya untuk Android O dan lebih baru)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    applicationContext.getString(R.string.notify_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(channel)
            }

            // Siapkan konten notifikasi
            val title = applicationContext.getString(R.string.notify_title, categoryName)
            val content = applicationContext.getString(R.string.notify_content, categoryTotal, categoryLimit)

            // Buat notifikasi
            val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher) // Ganti dengan ikon notifikasi Anda
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            // Tampilkan notifikasi
            notificationManager.notify(categoryId, notification)
        }

        return Result.success()
    }


}
