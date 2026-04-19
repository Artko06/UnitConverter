package com.example.converter.ui.model

import com.example.converter.ui.util.to2DigitFormat

sealed class MeasurementUnit(
    val symbol: String
) {
    sealed class Distance(
        symbol: String,
    ) : MeasurementUnit(symbol) {

        abstract fun fromMeters(value: Double): Double
        abstract fun toMeters(value: Double): Double

        object Meters : Distance("m") {
            override fun fromMeters(value: Double): Double = value

            override fun toMeters(value: Double): Double = value
        }

        object Kilometers : Distance("km") {
            override fun fromMeters(value: Double): Double = value * 0.001

            override fun toMeters(value: Double): Double = value * 1000
        }

        object Miles : Distance("mi") {
            override fun fromMeters(value: Double): Double = value * 0.000621371

            override fun toMeters(value: Double): Double = value * 1609.344
        }

        companion object {
            val allUnits by lazy { listOf(Meters, Kilometers, Miles) }

            fun convert(value: Double, from: Distance, to: Distance): String {
                val inMeters = from.toMeters(value)
                return to.fromMeters(inMeters).to2DigitFormat()
            }
        }
    }

    sealed class Weight(
        symbol: String,
    ) : MeasurementUnit(symbol) {

        abstract fun fromKilograms(value: Double): Double
        abstract fun toKilograms(value: Double): Double

        object Kilograms : Weight("kg") {
            override fun fromKilograms(value: Double): Double = value
            override fun toKilograms(value: Double): Double = value
        }

        object Grams : Weight("g") {
            override fun fromKilograms(value: Double): Double = value * 1000
            override fun toKilograms(value: Double): Double = value * 0.001
        }

        object Pounds : Weight("lb") {
            override fun fromKilograms(value: Double): Double = value * 2.20462
            override fun toKilograms(value: Double): Double = value * 0.453592
        }

        companion object {
            val allUnits by lazy { listOf(Kilograms, Grams, Pounds) }

            fun convert(value: Double, from: Weight, to: Weight): String {
                val inKilograms = from.toKilograms(value)
                return to.fromKilograms(inKilograms).to2DigitFormat()
            }
        }
    }

    sealed class Temperature(
        symbol: String
    ) : MeasurementUnit(symbol) {

        abstract fun toCelsius(value: Double): Double
        abstract fun fromCelsius(value: Double): Double

        object Celsius : Temperature("°C") {
            override fun toCelsius(value: Double): Double = value
            override fun fromCelsius(value: Double): Double = value
        }

        object Fahrenheit : Temperature("°F") {
            override fun toCelsius(value: Double): Double = (value - 32) * 5 / 9
            override fun fromCelsius(value: Double): Double = (value * 9 / 5) + 32
        }

        object Kelvin : Temperature("K") {
            override fun toCelsius(value: Double): Double = value - 273.15
            override fun fromCelsius(value: Double): Double = value + 273.15
        }

        companion object {
            val allUnits by lazy { listOf(Celsius, Fahrenheit, Kelvin) }

            fun convert(value: Double, from: Temperature, to: Temperature): String {
                val inCelsius = from.toCelsius(value)
                return to.fromCelsius(inCelsius).to2DigitFormat()
            }
        }
    }
}