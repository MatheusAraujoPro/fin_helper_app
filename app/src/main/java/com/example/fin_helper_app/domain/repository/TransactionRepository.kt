package com.example.fin_helper_app.domain.repository

import com.example.fin_helper_app.domain.model.TransactionModel
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAllTransactions(): Flow<List<TransactionModel>>
    fun storeTransaction(model: TransactionModel): Flow<Unit>
    fun deleteAll(): Flow<Unit>
    fun deleteById(id: Long): Flow<Unit>
}