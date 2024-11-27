package com.my.dailycashflow.ui.allcashflows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.PagingData
import com.my.dailycashflow.data.CashFlowWithCategory
import com.my.dailycashflow.data.DataRepository
import com.my.dailycashflow.util.CashFlowFilterType

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

}