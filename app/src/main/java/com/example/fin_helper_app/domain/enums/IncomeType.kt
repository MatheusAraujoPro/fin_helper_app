package com.example.fin_helper_app.domain.enums

enum class IncomeType(
    val value: Int,
    val description: String,
    val alternativeDescription: String? = null
) {
    NUBANK(value = 0, description = "Nubank"),
    GENIAL(value = 1, description = "Genial"),
    BALANCE(value = 2, description = "Saldo Livre", alternativeDescription = "Free Balance");

    companion object {
        fun value(value: Int) =
            entries.firstOrNull { it.value == value }
    }
}