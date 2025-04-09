package com.example.fin_helper_app.presentation.summary

import com.example.fin_helper_app.domain.model.TransactionModel
import com.example.fin_helper_app.ui.model.HeaderCardModel
import com.example.fin_helper_app.ui.model.HeaderCardType

data class SummaryScreenState(
    val transactionsList: List<TransactionModel> = listOf(),
    val isLoading: Boolean = false,
    val shouldShowBottomSheet: Boolean = false,
    val headerCardTypeMap: Map<HeaderCardType, Double> = mapOf(),
    val error: Throwable? = null,
    val shouldShowEditBottomSheet: Boolean = false,
)