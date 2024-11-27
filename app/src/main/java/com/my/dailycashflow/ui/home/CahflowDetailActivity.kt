package com.my.dailycashflow.ui.home

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.my.dailycashflow.R
import com.my.dailycashflow.data.ExpensesSummaryByCategory
import com.my.dailycashflow.util.EXPENSE
import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale

class CahflowDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cahflow_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil objek ExpensesSummaryByCategory dari Intent
        val expense = intent.getParcelableExtra<ExpensesSummaryByCategory>(EXPENSE)

        // Pastikan objek tidak null
        expense?.let {
            val categoryTextView: TextView = findViewById(R.id.textViewCategory)
            val totalExpenseTextView: TextView = findViewById(R.id.textViewAmount)
            val limitTextView: TextView = findViewById(R.id.textViewLimit)
            val typeTextView: TextView = findViewById(R.id.textViewType)

            // Menampilkan data lengkap ke UI
            categoryTextView.text = it.category.name
//            totalExpenseTextView.text = it.total.toString()
            val formattedTotalExpense = formatToIDR(it.total.toString().toDouble())
            totalExpenseTextView.text = formattedTotalExpense
            limitTextView.text = it.category.limit.toString()
            typeTextView.text = it.category.type

            // Menambahkan log untuk memverifikasi data
//            Toast.makeText(this, "Data received: ${it.toString()}", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(this, "No expense data found", Toast.LENGTH_SHORT).show()
        }
    }

    fun formatToIDR(amount: Double): String {
        val localeID = Locale("in", "ID") // Locale untuk Indonesia
        val decimalFormat = DecimalFormat("#,###") // Format untuk menampilkan angka tanpa desimal
        decimalFormat.currency = Currency.getInstance(localeID)

        return decimalFormat.format(amount) // Mengembalikan hasil format tanpa simbol 'Rp' dan desimal
    }
}