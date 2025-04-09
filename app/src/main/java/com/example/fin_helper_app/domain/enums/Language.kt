package com.example.fin_helper_app.domain.enums

enum class Language(
    val value: String,
) {
    PT_BR(value = "pt"),
    EN_US(value = "en");

    companion object {
        fun value(value: String) =
            values().firstOrNull { it.value == value }
    }
}