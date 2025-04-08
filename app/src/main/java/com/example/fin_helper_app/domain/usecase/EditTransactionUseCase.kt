package com.example.fin_helper_app.domain.usecase

import com.example.fin_helper_app.domain.UseCase
import com.example.fin_helper_app.domain.model.TransactionModel
import com.example.fin_helper_app.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope

class EditTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    scope: CoroutineScope
) : UseCase<Unit, EditTransactionUseCase.Params>(scope) {

    override fun run(params: Params?) = when (params?.model) {
        null -> throw NullPointerException()
        else -> transactionRepository.editTransaction(params.model)
    }

    data class Params(
        val model: TransactionModel
    )
}