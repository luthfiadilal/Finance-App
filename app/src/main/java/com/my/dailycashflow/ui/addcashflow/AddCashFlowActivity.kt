package com.my.dailycashflow.ui.addcashflow

import android.app.DatePickerDialog
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.my.dailycashflow.R
import com.my.dailycashflow.data.CashFlow
import com.my.dailycashflow.data.Category
import com.my.dailycashflow.data.CashFlowSummaryByCategory
import com.my.dailycashflow.util.*
import java.text.SimpleDateFormat
import java.util.*

class AddCashFlowActivity : AppCompatActivity() {

    private lateinit var viewModel: AddCashFlowViewModel

    private lateinit var spinnerType: Spinner
    private lateinit var spinnerCategory: Spinner
    private lateinit var adapterCategory: ArrayAdapter<Category>
    private var categorySelected: Category? = null

    private lateinit var edAmount: TextInputEditText
    private lateinit var edDate: TextInputEditText
    private lateinit var edInformation: TextInputEditText

    private lateinit var btnSave: Button

    private var dateInMillis: Long = 0L // Menyimpan dateInMillis yang akan digunakan

    private val expenseCategories = listOf(
        Category(1, "Food", 100000, "Expense"),
        Category(2, "Transport", 150000, "Expense"),
        Category(3, "Health", 100000, "Expense")
    )
    private val incomeCategories = listOf(
        Category(4, "Salary", 250000000, "Income"),
        Category(5, "Business", 5000000, "Income"),
        Category(6, "Freelance", 1000000, "Income")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cashflow)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.add_cashFlow)

        edDate = findViewById(R.id.edDate) // Pastikan ID ini benar di XML
        edAmount = findViewById(R.id.edAmount) // Pastikan ID ini benar di XML
        edInformation = findViewById(R.id.edInformation) // Pastikan ID ini benar di XML
        spinnerType = findViewById(R.id.spinnerType) // Pastikan ID ini benar di XML
        spinnerCategory = findViewById(R.id.spinnerCategory) // Pastikan ID ini benar di XML
        btnSave = findViewById(R.id.btnSave) // Pastikan ID ini benar di XML



        val factory = DataViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(AddCashFlowViewModel::class.java)

        viewModel.cashFlowSummaryByCategory.observe(this, Observer(this::setUpWorkManager))

        edDate.setOnClickListener {
            showDatePicker()
        }


        spinnerType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        0 -> {
                            // Kategori yang dipilih adalah "Expense"
                            spinnerCategory.visibility = View.VISIBLE
                            setExpenseCategories(expenseCategories)
                        }
                        1 -> {
                            // Kategori yang dipilih adalah "Income"
                            spinnerCategory.visibility = View.VISIBLE
                            setIncomeCategories(incomeCategories)
                        }
                    }
                    // Pastikan kategori yang dipilih disimpan dengan benar
                }
            }

        btnSave.setOnClickListener {
            if (isFieldInputFilled()) {
                val amount = edAmount.text.toString().toInt()
                val formattedDate = dateInMillis.convertLongToTime()
                val date = formattedDate.convertDateToMillis()
                val information = edInformation.text.toString()
                val type = spinnerType.selectedItem.toString()
                val category = categorySelected

                var cashFlow = CashFlow(
                    information = information,
                    dateInMillis = dateInMillis,
                    categoryId = categorySelected!!.id,
                    amount = amount,
                )

                viewModel.insertCashFlow(cashFlow)

                // Simpan atau update kategori jika belum ada
                categorySelected?.let {
                    // Jika kategori belum ada, insert kategori baru
                    viewModel.insertCategory(it)
                }


                finish()
            }
        }
    }




    private fun isFieldInputFilled(): Boolean {
        val checkAmountInput = edAmount.text?.isBlank() == false
        val checkDateInput = edDate.text?.isBlank() == false
        val checkInformationInput = edInformation.text?.isBlank() == false

        return checkAmountInput && checkDateInput && checkInformationInput
    }



    private fun setExpenseCategories(expenseCategories: List<Category>) {
        adapterCategory = ArrayAdapter(this, android.R.layout.simple_list_item_1, expenseCategories)
        spinnerCategory.apply {
            adapter = adapterCategory
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    categorySelected = expenseCategories[position]
                }
            }
        }
    }

    private fun setIncomeCategories(incomeCategories: List<Category>) {
        adapterCategory = ArrayAdapter(this, android.R.layout.simple_list_item_1, incomeCategories)
        spinnerCategory.adapter = adapterCategory
        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                categorySelected = incomeCategories[position]
            }
        }
    }


    private fun setUpWorkManager(cashFlowSummaryByCategory: CashFlowSummaryByCategory) {
        categorySelected?.let { category ->
            finish()
        }
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        val yearInit = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog( this, { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val format = "dd-MMM-yyyy"
                val sdf = SimpleDateFormat(format, Locale.US)
                edDate.setText(sdf.format(cal.timeInMillis))

                dateInMillis = cal.timeInMillis
            },
            yearInit,
            month,
            day
        )
        dpd.show()
    }




    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


}

