package com.my.dailycashflow.ui.expensessummary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.my.dailycashflow.R
import com.my.dailycashflow.data.ExpensesSummaryByCategory
import com.my.dailycashflow.ui.listexpenses.ListExpensesByCategoryActivity

class ExpensesSummaryByCategoryAdapter (
    private val onClick: (ExpensesSummaryByCategory) -> Unit
) : ListAdapter<ExpensesSummaryByCategory, ExpensesSummaryByCategoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesSummaryByCategoryViewHolder {
        return ExpensesSummaryByCategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_expense_category_item, parent, false))
    }

    override fun onBindViewHolder(holder: ExpensesSummaryByCategoryViewHolder, position: Int) {
        val item = getItem(position) // Ambil data dari adapter
        holder.bindData(item) // Pasangkan data dengan ViewHolder
        holder.itemView.setOnClickListener {
            onClick.invoke(item) // Panggil onClick dan berikan data yang benar (ExpensesSummaryByCategory)
        }

    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ExpensesSummaryByCategory>() {
            override fun areItemsTheSame(oldItem:ExpensesSummaryByCategory, newItem:ExpensesSummaryByCategory): Boolean {
                return oldItem.category.id == newItem.category.id
            }

            override fun areContentsTheSame(oldItem: ExpensesSummaryByCategory, newItem:ExpensesSummaryByCategory): Boolean {
                return oldItem == newItem
            }
        }

    }



}



