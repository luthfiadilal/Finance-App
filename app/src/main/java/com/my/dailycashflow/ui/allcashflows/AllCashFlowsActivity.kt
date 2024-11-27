package com.my.dailycashflow.ui.allcashflows

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.my.dailycashflow.R
import com.my.dailycashflow.ui.addcashflow.AddCashFlowActivity
import com.my.dailycashflow.ui.listcashflow.ListCashFlowAdapter
import com.my.dailycashflow.util.CashFlowFilterType
import com.my.dailycashflow.util.DataViewModelFactory

class AllCashFlowsActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var viewModel: AllCashFlowsViewModel
    private lateinit var adapterListCashFlow: ListCashFlowAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_cashflows)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.all_cash_flows)

        val factory = DataViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this,factory).get(AllCashFlowsViewModel::class.java)

        findViewById<FloatingActionButton>(R.id.fab_add).setOnClickListener {
            startActivity(Intent(this@AllCashFlowsActivity, AddCashFlowActivity::class.java))
        }

        val spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.list_type, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner = findViewById(R.id.spinner_filter)
        spinner.adapter = spinnerAdapter

        recyclerView = findViewById(R.id.recyclerview_cashflows)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapterListCashFlow = ListCashFlowAdapter()
        recyclerView.adapter = adapterListCashFlow

        viewModel.cashFlowsByType.observe(this) { pagingData ->
            adapterListCashFlow.submitData(lifecycle, pagingData)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.setFilterType(
                    when(position){
                        0 -> CashFlowFilterType.EXPENSE
                        1 -> CashFlowFilterType.INCOME
                        else -> CashFlowFilterType.EXPENSE
                    }
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}