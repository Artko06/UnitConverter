package com.example.converter.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.converter.ui.model.MeasurementUnit
import com.example.converter.ui.navigation.Routes
import com.example.converter.ui.util.copyToClipboard
import com.example.converter.ui.util.toDoubleWithPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConverterViewModel : ViewModel() {
    private var _currentRoute: MutableStateFlow<Routes> =
        MutableStateFlow(Routes.DestinationConverter)
    val currentRoute = _currentRoute.asStateFlow()

    private var _focusedField = MutableStateFlow(1)
    var focusedField = _focusedField.asStateFlow()

    private val _firstTextField = MutableStateFlow("0")
    val firstTextField = _firstTextField.asStateFlow()

    private val _secondTextField = MutableStateFlow("0")
    val secondTextField = _secondTextField.asStateFlow()

    private val _selectedMeasurementFirstField: MutableStateFlow<MeasurementUnit> =
        MutableStateFlow(MeasurementUnit.Distance.Kilometers)
    val selectedMeasurementFirstField = _selectedMeasurementFirstField.asStateFlow()

    private val _selectedMeasurementSecondField: MutableStateFlow<MeasurementUnit> =
        MutableStateFlow(MeasurementUnit.Distance.Meters)
    val selectedMeasurementSecondField = _selectedMeasurementSecondField.asStateFlow()

    fun updateCurrentRoute(route: Routes) {
        _currentRoute.value = route
        _firstTextField.value = "0"
        _secondTextField.value = "0"

        when (route) {
            Routes.DestinationConverter -> {
                _selectedMeasurementFirstField.value = MeasurementUnit.Distance.Kilometers
                _selectedMeasurementSecondField.value = MeasurementUnit.Distance.Meters
            }

            Routes.TemperatureConverter -> {
                _selectedMeasurementFirstField.value = MeasurementUnit.Temperature.Fahrenheit
                _selectedMeasurementSecondField.value = MeasurementUnit.Temperature.Celsius

                convertAndUpdateField()
            }

            Routes.WeightConverter -> {
                _selectedMeasurementFirstField.value = MeasurementUnit.Weight.Kilograms
                _selectedMeasurementSecondField.value = MeasurementUnit.Weight.Grams
            }
        }
    }

    fun updateFocusedField(newValue: Int) {
        _focusedField.value = newValue
    }

    fun updateSelectedMeasurementFirstField(newMeasurement: MeasurementUnit) {
        _selectedMeasurementFirstField.value = newMeasurement
        convertAndUpdateField()
    }

    fun updateSelectedMeasurementSecondField(newMeasurement: MeasurementUnit) {
        _selectedMeasurementSecondField.value = newMeasurement
        convertAndUpdateField()
    }

    fun swapUnits() {
        val tempFirstTextField = _firstTextField.value
        _firstTextField.value = _secondTextField.value
        _secondTextField.value = tempFirstTextField

        val tempMeasurementFirstField = _selectedMeasurementFirstField.value
        _selectedMeasurementFirstField.value = _selectedMeasurementSecondField.value
        _selectedMeasurementSecondField.value = tempMeasurementFirstField
    }

    fun copyValues(
        context: Context
    ) {
        copyToClipboard(
            context = context,
            textToCopy =  "${_firstTextField.value} ${_selectedMeasurementFirstField.value.symbol} = " +
                    "${_secondTextField.value} ${_selectedMeasurementSecondField.value.symbol}"
        )
    }

    fun handleDigit(digit: String) {
        val currentValue = when (_focusedField.value) {
            1 -> _firstTextField.value
            2 -> _secondTextField.value
            else -> return
        }

        val newValue = if (currentValue == "0") digit else currentValue + digit

        when (_focusedField.value) {
            1 -> _firstTextField.value = newValue
            2 -> _secondTextField.value = newValue
        }

        convertAndUpdateField()
    }

    fun handleDecimal() {
        val currentValue = when (_focusedField.value) {
            1 -> _firstTextField.value
            2 -> _secondTextField.value
            else -> return
        }

        if (!currentValue.contains(".")) {
            val newValue = if (currentValue == "0") "0." else "$currentValue."
            when (_focusedField.value) {
                1 -> _firstTextField.value = newValue
                2 -> _secondTextField.value = newValue
            }
        }
    }

    fun handleSignChange() {
        val currentValue = when (_focusedField.value) {
            1 -> _firstTextField.value
            2 -> _secondTextField.value
            else -> return
        }

        when(currentRoute.value) {
            Routes.DestinationConverter -> return
            Routes.TemperatureConverter -> {
                when (_focusedField.value) {
                    1 -> {
                        if (_firstTextField.value.first() == '-')
                            _firstTextField.value = _firstTextField.value.drop(1)
                        else _firstTextField.value = "-${_firstTextField.value}"
                    }
                    2 -> {
                        if (_secondTextField.value.first() == '-')
                            _secondTextField.value = _secondTextField.value.drop(1)
                        else _secondTextField.value = "-${_secondTextField.value}"
                    }
                }
            }
            Routes.WeightConverter -> return
        }

        convertAndUpdateField()
    }

    fun handleBackspace() {
        val currentValue = when (_focusedField.value) {
            1 -> _firstTextField.value
            2 -> _secondTextField.value
            else -> return
        }

        val newValue = if (currentValue.length > 1) currentValue.dropLast(1) else "0"

        when (_focusedField.value) {
            1 -> _firstTextField.value = newValue
            2 -> _secondTextField.value = newValue
        }

        convertAndUpdateField()
    }

    fun handleClear() {
        _firstTextField.value = "0"
        _secondTextField.value = "0"

        when(_currentRoute.value) {
            Routes.DestinationConverter -> return
            Routes.TemperatureConverter -> convertAndUpdateField()
            Routes.WeightConverter -> return
        }
    }

    private fun convertAndUpdateField() {
        when (_focusedField.value) {
            1 -> {
                val newValue = convertValue(
                    value = _firstTextField.value.toDoubleWithPoint(),
                    from = _selectedMeasurementFirstField.value,
                    to = _selectedMeasurementSecondField.value
                )

                _secondTextField.value = newValue
            }

            2 -> {
                val newValue = convertValue(
                    value = _secondTextField.value.toDoubleWithPoint(),
                    from = _selectedMeasurementSecondField.value,
                    to = _selectedMeasurementFirstField.value
                )

                _firstTextField.value = newValue
            }
        }
    }

    private fun convertValue(value: Double, from: MeasurementUnit, to: MeasurementUnit): String {
        return when (_currentRoute.value) {
            Routes.TemperatureConverter -> {
                val fromTemp = from as MeasurementUnit.Temperature
                val toTemp = to as MeasurementUnit.Temperature
                MeasurementUnit.Temperature.convert(value, fromTemp, toTemp)
            }

            Routes.DestinationConverter -> {
                val fromDist = from as MeasurementUnit.Distance
                val toDist = to as MeasurementUnit.Distance
                MeasurementUnit.Distance.convert(value, fromDist, toDist)
            }

            Routes.WeightConverter -> {
                val fromWeight = from as MeasurementUnit.Weight
                val toWeight = to as MeasurementUnit.Weight
                MeasurementUnit.Weight.convert(value, fromWeight, toWeight)
            }
        }
    }
}