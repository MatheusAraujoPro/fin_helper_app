package com.example.fin_helper_app.data.datasource.local

import com.example.fin_helper_app.data.model.TransactionsMapper
import com.example.fin_helper_app.domain.enums.IncomeType
import com.example.fin_helper_app.domain.model.TransactionModel
import com.example.fin_helper_app.ui.components.TransactionType
import data.sqldelight.database.Transactions
import kotlinx.coroutines.flow.flow

class DataLocalDataSource(
    private val transactionsDataBase: Transactions
) {
    fun getAllTransactions() = flow {
        val result =
            transactionsDataBase.transactionsQueries.selectAllTransactions().executeAsList()

        emit(TransactionsMapper.toDomain(result))
    }

    fun storeTransaction(transaction: TransactionModel) = flow {
        emit(
            transactionsDataBase.transactionsQueries.insertTransaction(
                name = transaction.name,
                value_ = transaction.value,
                incomeType = IncomeType.value(transaction.incomeType.value)?.value?.toLong() ?: 0L,
                type = TransactionType.value(transaction.type.value)?.value?.toLong() ?: 0L,
                createdAt = transaction.createdAt
            )
        )
    }

    fun deleteAll() = flow {
        val result = transactionsDataBase.transactionsQueries.deleteAll()
        emit(result)
    }

    fun deleteById(id: Long) = flow {
        val result = transactionsDataBase.transactionsQueries.deleteById(id)
        emit(result)
    }

    fun editTransaction(transaction: TransactionModel) = flow {
        val result = transaction.id?.let {
            transactionsDataBase.transactionsQueries.editTransaction(
                name = transaction.name,
                value_ = transaction.value,
                createdAt = transaction.createdAt,
                incomeType = IncomeType.value(transaction.incomeType.value)?.value?.toLong() ?: 0L,
                type = TransactionType.value(transaction.type.value)?.value?.toLong() ?: 0L,
                transaction_id = it
            )
        }
        emit(result!!)
    }
}