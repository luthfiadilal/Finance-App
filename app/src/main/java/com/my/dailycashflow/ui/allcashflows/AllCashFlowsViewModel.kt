package com.my.dailycashflow.ui.allcashflows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.my.dailycashflow.data.CashFlowWithCategory
import com.my.dailycashflow.data.DataRepository
import com.my.dailycashflow.util.CashFlowFilterType
import kotlinx.coroutines.launch

class AllCashFlowsViewModel(private val repository: DataRepository) : ViewModel() {

    private val _filterType = MutableLiveData<CashFlowFilterType>()

    init {
        _filterType.value = CashFlowFilterType.EXPENSE
    }

    val cashFlowsByType: LiveData<PagingData<CashFlowWithCategory>> =
        _filterType.switchMap { filterType ->
            repository.getCashFlowsByType(filterType)
        }

    fun setFilterType(filterType: CashFlowFilterType) {
        _filterType.value = filterType
    }

    fun deleteCashFlow(item: CashFlowWithCategory) {
        viewModelScope.launch {
            repository.deleteCashFlow(item.cashFlow)
        }
    }

}