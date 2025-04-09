package com.example.fin_helper_app.domain.model

import com.example.fin_helper_app.domain.enums.IncomeType
import com.example.fin_helper_app.ui.components.TransactionType

data class TransactionModel(
    val id: Long? = null,
    val name: String = "",
    val type: TransactionType = TransactionType.REVENUE,
    val value: Double = 0.0,
    val createdAt: String = "",
    val incomeType: IncomeType = IncomeType.BALANCE
)