package com.example.converter.ui.navigation

sealed class Routes(val name: String) {
    object TemperatureConverter: Routes(name = "Temperature")
    object DestinationConverter: Routes(name = "Destination")
    object WeightConverter: Routes(name = "Weight")

    companion object {
        val allDestinations: List<Routes> by lazy {
            listOf(DestinationConverter, TemperatureConverter, WeightConverter)
        }
    }
}