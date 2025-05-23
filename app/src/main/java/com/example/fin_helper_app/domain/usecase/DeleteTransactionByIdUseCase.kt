package com.example.fin_helper_app.domain.usecase

import com.example.fin_helper_app.domain.UseCase
import com.example.fin_helper_app.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope

class DeleteTransactionByIdUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    scope: CoroutineScope
) : UseCase<Unit, DeleteTransactionByIdUseCase.Params>(scope) {

    override fun run(params: Params?) = when (params?.id) {
        null -> throw NullPointerException()
        else -> transactionRepository.deleteById(params.id)
    }

    data class Params(
        val id: Long
    )
}