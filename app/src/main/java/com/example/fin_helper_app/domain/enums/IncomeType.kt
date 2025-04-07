package com.example.fin_helper_app.domain.enums

enum class IncomeType(val value: Int, val description: String) {
    NUBANK(value = 0, description = "Nubank"),
    GENIAL(value = 1, description = "Genial"),
    BALANCE(value = 2, description = "Saldo Livre");

    companion object {
        fun value(value: Int) =
            IncomeType.values().firstOrNull { it.value == value }
    }
}