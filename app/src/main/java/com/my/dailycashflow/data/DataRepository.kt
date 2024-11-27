package com.my.dailycashflow.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.my.dailycashflow.util.CashFlowFilterType
import com.my.dailycashflow.util.FilterUtils
import java.util.concurrent.Executors

class   DataRepository(private val cashFlowDao: CashFlowDao) {

    // Mendapatkan ringkasan arus kas berdasarkan kategori
    fun getCashFlowSummary(): LiveData<List<CashFlowSummaryByCategory>> {
        return cashFlowDao.getCashFlowSummary()
    }

    // Mendapatkan arus kas terbaru
    fun getRecentCashFlow() : LiveData<CashFlowWithCategory> = cashFlowDao.getRecentCashFlow()

    // Mendapatkan ringkasan pengeluaran berdasarkan kategori
    fun getExpensesSummaryByCategory(): LiveData<List<ExpensesSummaryByCategory>> {
        return cashFlowDao.getExpensesSummaryByCategory()
    }

    // Mendapatkan semua arus kas berdasarkan tipe (pengeluaran/pendapatan)
    fun getCashFlowsByType(filterType: CashFlowFilterType): LiveData<PagingData<CashFlowWithCategory>> {
        val query = FilterUtils.getFilterQuery(filterType) // Mengambil query berdasarkan filter tipe

        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { cashFlowDao.getAllCashFlowByType(query) }
        ).liveData
    }

    // Mendapatkan pengeluaran berdasarkan kategori tertentu
    fun getExpensesByCategory(categoryId: Int): LiveData<PagingData<CashFlowWithCategory>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { cashFlowDao.getExpensesByCategory(categoryId) }
        ).liveData
    }

    // Mendapatkan kategori tipe pengeluaran
    fun getExpenseTypeCategories(): LiveData<List<Category>> {
        return cashFlowDao.getCategoriesByType("Expense")
    }

    // Menambahkan arus kas baru
    fun insertCashFlow(data: CashFlow) {
        executeThread {
            cashFlowDao.insertCashFlow(data)
            Log.d("REPOSITORY ADD", "DATA: $data")
        }
    }

    // Menyimpan kategori yang dipilih
    fun insertCategory(category: Category) {
        executeThread {
            cashFlowDao.insertCategory(category)
            Log.d("REPOSITORY ADD", "CATEGORY: $category")
        }
    }

    // Menghapus arus kas
    fun deleteCashFlow(data: CashFlow) {
        executeThread {
            cashFlowDao.deleteCashFlow(data)
        }
    }

    // Mendapatkan ringkasan arus kas berdasarkan kategori tertentu
    fun getCashFlowSummaryByCategory(categoryId: Int): LiveData<CashFlowSummaryByCategory> {
        return cashFlowDao.getCashFlowSummaryByCategory(categoryId)
    }

    // Menjalankan tugas di thread terpisah
    private fun executeThread(f: () -> Unit) {
        Executors.newSingleThreadExecutor().execute(f)
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        // Singleton untuk memastikan hanya ada satu instance DataRepository
        fun getInstance(context: Context): DataRepository {
            return instance ?: synchronized(DataRepository::class.java) {
                if (instance == null) {
                    val database = CashFlowDatabase.getInstance(context)
                    instance = DataRepository(database.cashFlowDao())
                }
                return instance as DataRepository
            }
        }
    }
}