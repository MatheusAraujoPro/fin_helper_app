package com.example.fin_helper_app.domain.usecase

import com.example.fin_helper_app.domain.UseCase
import com.example.fin_helper_app.domain.model.TransactionModel
import com.example.fin_helper_app.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class StoreTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    scope: CoroutineScope
) : UseCase<Unit, StoreTransactionUseCase.Params>(scope) {

    override fun run(params: Params?) = when (params) {
        null -> throw Exception()
        else -> transactionRepository.storeTransaction(params.model)
    }

    data class Params(
        val model: TransactionModel
    )
}