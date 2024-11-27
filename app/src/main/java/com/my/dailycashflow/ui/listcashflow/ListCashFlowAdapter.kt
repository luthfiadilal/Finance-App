package com.my.dailycashflow.ui.listcashflow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.my.dailycashflow.R
import com.my.dailycashflow.data.CashFlowWithCategory

class ListCashFlowAdapter :
    PagingDataAdapter<CashFlowWithCategory, ListCashFlowViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCashFlowViewHolder {
        return ListCashFlowViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_cashflows_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListCashFlowViewHolder, position: Int) {
        (getItem(position))?.let { holder.bindData(it) }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CashFlowWithCategory>() {
            override fun areItemsTheSame(
                oldItem: CashFlowWithCategory,
                newItem: CashFlowWithCategory
            ): Boolean {
                return oldItem.category!!.type == newItem.category!!.type
            }

            override fun areContentsTheSame(
                oldItem: CashFlowWithCategory,
                newItem: CashFlowWithCategory
            ): Boolean {
                return oldItem == newItem
            }
        }

    }

}