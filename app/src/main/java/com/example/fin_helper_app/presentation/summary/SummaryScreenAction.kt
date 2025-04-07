package com.example.fin_helper_app.presentation.summary

import com.example.fin_helper_app.domain.model.TransactionModel

sealed class SummaryScreenAction {
    data class SaveTransaction(val model: TransactionModel) : SummaryScreenAction()
    data class DeleteTransactionById(val transactionId: Long) : SummaryScreenAction()

    data object GetTransactions : SummaryScreenAction()
    data object ShouldShowBottomSheet : SummaryScreenAction()
    data object DeleteTransaction : SummaryScreenAction()
}
