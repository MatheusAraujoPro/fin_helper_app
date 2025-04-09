package com.example.fin_helper_app.data.repository

import com.example.fin_helper_app.data.datasource.local.DataLocalDataSource
import com.example.fin_helper_app.domain.model.TransactionModel
import com.example.fin_helper_app.domain.repository.TransactionRepository
import jakarta.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dataSource: DataLocalDataSource
) : TransactionRepository {
    override fun getAllTransactions() = dataSource.getAllTransactions()
    override fun storeTransaction(model: TransactionModel) = dataSource.storeTransaction(model)
    override fun deleteAll() = dataSource.deleteAll()
    override fun deleteById(id: Long) = dataSource.deleteById(id)
    override fun editTransaction(model: TransactionModel) = dataSource.editTransaction(model)
}