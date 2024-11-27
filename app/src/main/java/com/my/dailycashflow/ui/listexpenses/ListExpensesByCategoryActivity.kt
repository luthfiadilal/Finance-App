package com.my.dailycashflow.ui.listexpenses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.my.dailycashflow.R
import com.my.dailycashflow.data.CashFlow
import com.my.dailycashflow.data.CashFlowWithCategory
import com.my.dailycashflow.ui.listcashflow.ListCashFlowAdapter
import com.my.dailycashflow.ui.listcashflow.ListCashFlowViewHolder
import com.my.dailycashflow.util.CATEGORY_ID
import com.my.dailycashflow.util.DataViewModelFactory
import com.my.dailycashflow.util.Event

class ListExpensesByCategoryActivity : AppCompatActivity() {

    private lateinit var rvExpenses: RecyclerView
    private lateinit var adapterExpenses: ListCashFlowAdapter

    private lateinit var viewModel: ListExpensesByCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_expenses_by_category)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.list_expenses_summary)

        val id = intent.getIntExtra(CATEGORY_ID, 0)

        val factory = DataViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(ListExpensesByCategoryViewModel::class.java)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
//        throw NotImplementedError("needs implementation")
    }

    private fun showExpenseData(data: PagingData<CashFlowWithCategory>) {
        adapterExpenses.submitData(lifecycle, data)
    }

    private fun showSnackBar(eventMessage: Event<Int>) {
        val message = eventMessage.getContentIfNotHandled() ?: return
        Snackbar.make(
            findViewById(R.id.vg_listExpenses),
            getString(message),
            Snackbar.LENGTH_SHORT
        ).setAction("Undo"){
            viewModel.insertCashFlow(viewModel.undo.value?.getContentIfNotHandled() as CashFlow)
        }.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    inner class ItemTouchCallBack : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val expenseByCategory = (viewHolder as ListCashFlowViewHolder).getCashFlowByCategoryData()
            viewModel.deleteCashFlow(expenseByCategory.cashFlow)
        }
    }
}