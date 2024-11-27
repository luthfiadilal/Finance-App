package com.my.dailycashflow.ui.listcashflow

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.dailycashflow.R
import com.my.dailycashflow.data.CashFlowWithCategory
import com.my.dailycashflow.util.convertLongToTime
import com.my.dailycashflow.util.formatToIDR

class ListCashFlowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var cashFlowWithCategoryData: CashFlowWithCategory

    fun bindData(data: CashFlowWithCategory) {
        cashFlowWithCategoryData = data

        val tvDate: TextView = itemView.findViewById(R.id.text_date)
        val tvType: TextView = itemView.findViewById(R.id.text_type)
        val tvCategory: TextView = itemView.findViewById(R.id.text_category)
        val tvInformation: TextView = itemView.findViewById(R.id.text_information)
        val tvPrice: TextView = itemView.findViewById(R.id.text_price)

        val expenseType = data.category!!.type

        tvType.text = expenseType
        tvCategory.text = if (expenseType != "Income") data.category.name else ""
        tvInformation.text = data.cashFlow.information
        tvPrice.text = data.cashFlow.amount!!.formatToIDR()
        tvDate.text = data.cashFlow.dateInMillis!!.convertLongToTime()
    }

    fun getCashFlowByCategoryData(): CashFlowWithCategory = cashFlowWithCategoryData

}