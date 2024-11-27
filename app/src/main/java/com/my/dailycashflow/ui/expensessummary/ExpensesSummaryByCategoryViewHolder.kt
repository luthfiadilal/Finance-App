package com.my.dailycashflow.ui.expensessummary

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.my.dailycashflow.R
import com.my.dailycashflow.data.ExpensesSummaryByCategory
import com.my.dailycashflow.util.formatToIDR

class ExpensesSummaryByCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(expenses: ExpensesSummaryByCategory) {
        val tvCategory: TextView = itemView.findViewById(R.id.text_category)
        val tvTotalExpense: TextView = itemView.findViewById(R.id.text_total_expenses)
        val tvLimit: TextView = itemView.findViewById(R.id.text_limited)

        // Pastikan category tidak null sebelum mengakses data
        val category = expenses.category

        if (category != null) {
            tvCategory.text = category.name ?: "No Category"
            tvTotalExpense.text = expenses.total?.formatToIDR() ?: "Rp 0"
            tvLimit.text = category.limit?.formatToIDR() ?: "Rp 0"

            // Pengecekan jika total pengeluaran lebih besar dari limit
            if (expenses.total != null && category.limit != null && expenses.total >= category.limit) {
                tvTotalExpense.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.holo_red_dark))
            } else {
                tvTotalExpense.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.black))
            }
        } else {
            // Jika category null, tampilkan nilai default
            tvCategory.text = "No Category"
            tvTotalExpense.text = "Rp 0"
            tvLimit.text = "Rp 0"
        }
    }
}
