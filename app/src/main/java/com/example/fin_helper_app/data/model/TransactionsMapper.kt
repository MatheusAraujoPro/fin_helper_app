package com.example.fin_helper_app.data.model

import android.util.Log
import com.example.fin_helper_app.data.DataLocalMapper
import com.example.fin_helper_app.domain.enums.IncomeType
import com.example.fin_helper_app.domain.model.TransactionModel
import com.example.fin_helper_app.ui.components.TransactionType
import data.sqldelight.database.SelectAllTransactions

object TransactionsMapper : DataLocalMapper<List<SelectAllTransactions>, List<TransactionModel>>() {
    override fun toDomain(data: List<SelectAllTransactions>): List<TransactionModel> {
        val transactionsList = mutableListOf<TransactionModel>()
        data.map {
            transactionsList.add(
                TransactionModel(
                    id = it.transaction_id,
                    name = it.name,
                    value = it.value_,
                    type = TransactionType.value(it.type.toInt())!!,
                    incomeType = IncomeType.value(it.incomeType.toInt())!!,
                    createdAt = it.createdAt
                )
            )
        }
        return transactionsList
    }
}