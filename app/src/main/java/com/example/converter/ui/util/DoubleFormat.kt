package com.example.converter.ui.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Double.to2DigitFormat(): String {
    val df = DecimalFormat("0.####", DecimalFormatSymbols.getInstance(Locale.US))
    return df.format(this)
}