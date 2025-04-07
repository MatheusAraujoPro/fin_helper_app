package com.example.fin_helper_app.domain.usecase

import com.example.fin_helper_app.domain.UseCase
import com.example.fin_helper_app.domain.model.TransactionModel
import com.example.fin_helper_app.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope

class GetTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    scope: CoroutineScope
) : UseCase<List<TransactionModel>, Unit>(scope) {
    override fun run(params: Unit?) = transactionRepository.getAllTransactions()
}