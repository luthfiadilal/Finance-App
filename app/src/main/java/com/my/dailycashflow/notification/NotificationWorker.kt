package com.my.dailycashflow.notification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.my.dailycashflow.R
import com.my.dailycashflow.util.CATEGORY_ID
import com.my.dailycashflow.util.CATEGORY_LIMIT
import com.my.dailycashflow.util.CATEGORY_NAME
import com.my.dailycashflow.util.CATEGORY_TOTAL

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val categoryId = inputData.getInt(CATEGORY_ID, 0)
    private val categoryName = inputData.getString(CATEGORY_NAME)
    private val categoryTotal = inputData.getInt(CATEGORY_TOTAL, 0)
    private val categoryLimit = inputData.getInt(CATEGORY_LIMIT, 0)

    override fun doWork(): Result {
        val prefManager = androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val shouldNotify = prefManager.getBoolean(applicationContext.getString(R.string.pref_key_notify), false)

        return Result.success()
    }

}
