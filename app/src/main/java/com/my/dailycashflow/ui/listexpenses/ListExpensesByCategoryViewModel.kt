package com.my.dailycashflow.ui.listexpenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.my.dailycashflow.R
import com.my.dailycashflow.data.DataRepository
import com.my.dailycashflow.data.CashFlowWithCategory
import com.my.dailycashflow.data.CashFlow
import com.my.dailycashflow.util.Event

class ListExpensesByCategoryViewModel(private val repository: DataRepository) : ViewModel() {

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _undo = MutableLiveData<Event<CashFlow>>()
    val undo: LiveData<Event<CashFlow>> = _undo

//    fun getExpensesByCategory(categoryId: Int): LiveData<PagingData<CashFlowWithCategory>> =
//        throw NotImplementedError("needs implementation")

    fun deleteCashFlow(cashFlow: CashFlow){
        repository.deleteCashFlow(cashFlow)
        _snackbarText.value = Event(R.string.expenses_deleted)
        _undo.value = Event(cashFlow)
    }

    fun insertCashFlow(cashFlow: CashFlow) {
        repository.insertCashFlow(cashFlow)
    }

}