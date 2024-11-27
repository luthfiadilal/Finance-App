package com.my.dailycashflow.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.my.dailycashflow.R

class AllSummarySection @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs) {

    private var tvIncomeValue: TextView
    private var tvExpenseValue: TextView
    private var tvBalanceValue: TextView

    init {
        val rootView = inflate(context, R.layout.all_summary_view, this)

        tvIncomeValue = rootView.findViewById(R.id.text_income_home)
        tvExpenseValue = rootView.findViewById(R.id.text_expense_home)
        tvBalanceValue = rootView.findViewById(R.id.text_balance_home)
    }

    fun setSummaryData(income: String, expenses: String, balance: String){
        tvIncomeValue.text = income
        tvExpenseValue.text = expenses
        tvBalanceValue.text = balance
    }

}