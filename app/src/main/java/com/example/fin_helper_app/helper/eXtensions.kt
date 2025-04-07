package com.example.fin_helper_app.helper

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Number.asCurrency(): String = NumberFormat
    .getCurrencyInstance(Locale("pt", "BR"))
    .format(this.toDouble())

@Throws(NumberFormatException::class)
fun Date.getDayMonthAndYear() = format("dd/MM/yyyy")


fun String.capitalized(): String {
    return this.lowercase().replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else
            it.toString()
    }
}

fun String.capSentences(): String {
    return this.split(" ").joinToString(" ") {
        it.capitalized()
    }
}

private fun Date.format(format: String): String = SimpleDateFormat(
    format,
    Locale("pt", "BR")
).format(this)