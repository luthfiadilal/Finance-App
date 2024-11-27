package com.my.dailycashflow.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.my.dailycashflow.data.DataRepository
import com.my.dailycashflow.ui.addcashflow.AddCashFlowViewModel
import com.my.dailycashflow.ui.allcashflows.AllCashFlowsViewModel
import com.my.dailycashflow.ui.listexpenses.ListExpensesByCategoryViewModel
import com.my.dailycashflow.ui.home.MainViewModel

class DataViewModelFactory private constructor(private val dataRepository: DataRepository) :
    ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: DataViewModelFactory? = null

        fun getInstance(context: Context): DataViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DataViewModelFactory(
                    DataRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(ListExpensesByCategoryViewModel::class.java) -> {
                ListExpensesByCategoryViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(AllCashFlowsViewModel::class.java) -> {
                AllCashFlowsViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(AddCashFlowViewModel::class.java) -> {
                AddCashFlowViewModel(dataRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}
