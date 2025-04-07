package com.example.fin_helper_app.ui.model

enum class HeaderCardType(val value: Int) {
    BALANCE(1),
    GENIAL_CARD(2),
    NUBANK_CARD(3),
    INVESTMENTS(4);

    companion object {
        fun value(value: Int) =
            HeaderCardType.values().firstOrNull { it.value == value }
    }
}