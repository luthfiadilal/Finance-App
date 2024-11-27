package com.my.dailycashflow.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.my.dailycashflow.R
import com.my.dailycashflow.data.CashFlow
import com.my.dailycashflow.data.CashFlowSummaryByCategory
import com.my.dailycashflow.data.CashFlowWithCategory
import com.my.dailycashflow.data.ExpensesSummaryByCategory
import com.my.dailycashflow.ui.addcashflow.AddCashFlowActivity
import com.my.dailycashflow.ui.allcashflows.AllCashFlowsActivity
import com.my.dailycashflow.ui.customview.AllSummarySection
import com.my.dailycashflow.ui.expensessummary.ExpensesSummaryByCategoryAdapter
import com.my.dailycashflow.ui.setting.SettingActivity
import com.my.dailycashflow.util.DataViewModelFactory
import com.my.dailycashflow.util.EXPENSE
import com.my.dailycashflow.util.convertLongToTime
import com.my.dailycashflow.util.formatToIDR
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterExpensesSummaryByCategory: ExpensesSummaryByCategoryAdapter
    private lateinit var allSummarySection: AllSummarySection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = DataViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        val fabAddCashFlow: FloatingActionButton = findViewById(R.id.fab_add_cashflow)
        allSummarySection = findViewById(R.id.all_summary_section)  // CustomView placeholder

        // Menambahkan listener untuk klik
        fabAddCashFlow.setOnClickListener {
            // Navigasi ke AddCashFlowActivity
            val intent = Intent(this, AddCashFlowActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_all_cashflows).setOnClickListener {
            startActivity(Intent(this@MainActivity, AllCashFlowsActivity::class.java))
        }

        findViewById<ImageView>(R.id.btn_setting).setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingActivity::class.java))
        }

        viewModel.getCashFlowSummaryByCategory.observe(this) {
            showCashFlowSummary(it)
        }

        viewModel.getRecentCashFlow.observe(this) { data ->
            data?.let {
                recentCashFlow(it)
            } ?: run {
                Log.e("MainActivity", "Recent CashFlow data is null")
                // Bisa menambahkan tindakan jika data null, misalnya memberi pesan ke UI
            }
        }

        adapterExpensesSummaryByCategory = ExpensesSummaryByCategoryAdapter(
            onClick = {
                val intent = Intent(this, CahflowDetailActivity::class.java)
                intent.putExtra(EXPENSE, it)
                Log.d("EXPENSE", it.toString())
                startActivity(intent)
            }
        )
        val rvExpensesSummaryByCategory = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerview_expenses_by_category)
        // Menambahkan LayoutManager
        rvExpensesSummaryByCategory.layoutManager = LinearLayoutManager(this)
        rvExpensesSummaryByCategory.adapter = adapterExpensesSummaryByCategory

        viewModel.getExpensesSummaryByCategory.observe(this) { list ->
            adapterExpensesSummaryByCategory.submitList(list){
                Log.d("ExpensesSummary", "List size: ${list.size}")
                adapterExpensesSummaryByCategory.notifyDataSetChanged()
            }
        }
    }

    private fun recentCashFlow(data: CashFlowWithCategory) {
        val viewGroup = findViewById<CardView>(R.id.vg_recentCashFlow)

        val textDate = viewGroup.findViewById<TextView>(R.id.text_date)
        val textInformation = viewGroup.findViewById<TextView>(R.id.text_information)
        val textPrice = viewGroup.findViewById<TextView>(R.id.text_price)

        // variable untuk menampilkan data type (Income atau Expanse)
        val textType = viewGroup.findViewById<TextView>(R.id.text_type)

        // variable untuk menampilkan data kategori
        val textKategory = viewGroup.findViewById<TextView>(R.id.text_category)


        // Pastikan data.cashFlow tidak null
        val cashFlow = data.cashFlow
        if (cashFlow != null) {
            textDate.text = cashFlow.dateInMillis?.convertLongToTime() ?: "No date available"
            textInformation.text = cashFlow.information ?: "No information available"
            textPrice.text = cashFlow.amount?.formatToIDR() ?: "No price available"
        } else {
            // Handle jika cashFlow null
            textDate.text = "No date available"
            textInformation.text = "No information available"
            textPrice.text = "No price available"
        }

        // Pastikan category tidak null
        val category = data.category
        textKategory.text = category?.name ?: "Uncategorized"
        textType.text = category?.type ?: "Unknown"




    }

    private fun showCashFlowSummary(data: List<CashFlowSummaryByCategory>) {
        var totalIncome = 0
        var totalExpense = 0
        var totalBalance = 0

        // Mengiterasi data untuk menghitung total income dan expense
        for (summary in data) {
            // Anda bisa menyesuaikan logika ini tergantung apakah kategori tersebut adalah income atau expense
            if (summary.category?.type == "Income") {
                totalIncome += summary.total
            } else if (summary.category?.type == "Expense") {
                totalExpense += summary.total
            }
        }

        // Menghitung saldo
        totalBalance = totalIncome - totalExpense

        // Format hasil dalam IDR (Rupiah)
        val incomeText = formatToIDR(totalIncome.toDouble())
        val expenseText = formatToIDR(totalExpense.toDouble())
        val balanceText = formatToIDR(totalBalance.toDouble())

        // Menampilkan data ke custom view
        allSummarySection.setSummaryData(incomeText, expenseText, balanceText)
    }
    fun formatToIDR(amount: Double): String {
        val localeID = Locale("in", "ID") // Locale untuk Indonesia
        val decimalFormat = DecimalFormat("#,###") // Format untuk menampilkan angka tanpa desimal
        decimalFormat.currency = Currency.getInstance(localeID)

        return decimalFormat.format(amount) // Mengembalikan hasil format tanpa simbol 'Rp' dan desimal
    }

    private fun showExpenseDetail(expenseSummary: ExpensesSummaryByCategory) {
        // Navigasi atau tindakan yang diinginkan saat item diklik
        val intent = Intent(this, CahflowDetailActivity::class.java)
        intent.putExtra("EXPENSE_ID", expenseSummary.category.id)
        startActivity(intent)
    }
}