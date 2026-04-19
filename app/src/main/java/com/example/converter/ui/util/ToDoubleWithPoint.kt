package com.example.converter.ui.util

fun String.toDoubleWithPoint(): Double {
    val str = this

    return if (str.last() == '.') {
        (str + "0").toDouble()
    } else str.toDouble()
}