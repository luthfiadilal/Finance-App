package com.my.dailycashflow.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.my.dailycashflow.data.CashFlow
import com.my.dailycashflow.data.DataRepository
import com.my.dailycashflow.data.ExpensesSummaryByCategory
import com.my.dailycashflow.data.CashFlowSummaryByCategory
import com.my.dailycashflow.data.CashFlowWithCategory

class MainViewModel(repository: DataRepository) : ViewModel() {

    val getCashFlowSummaryByCategory: LiveData<List<CashFlowSummaryByCategory>> = repository.getCashFlowSummary()

    val getRecentCashFlow: LiveData<CashFlowWithCategory> =
        repository.getRecentCashFlow()

    val getExpensesSummaryByCategory: LiveData<List<ExpensesSummaryByCategory>> =
        repository.getExpensesSummaryByCategory()



}