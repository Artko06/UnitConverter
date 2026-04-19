package com.example.converter.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.converter.R
import com.example.converter.ui.components.ActionIconButton
import com.example.converter.ui.components.ConverterTextField
import com.example.converter.ui.components.keyboard.CalculatorKeyboard
import com.example.converter.ui.model.MeasurementUnit
import com.example.converter.ui.navigation.Routes
import com.example.converter.ui.viewModel.ConverterViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(
    converterViewModel: ConverterViewModel
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        topBar = {
            TopAppNavBar(
                converterViewModel = converterViewModel
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLandscape) {
                LandscapeLayout(
                    converterViewModel = converterViewModel
                )
            } else {
                PortraitLayout(
                    converterViewModel = converterViewModel
                )
            }
        }
    }
}

@Composable
private fun PortraitLayout(
    converterViewModel: ConverterViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TextFieldCard(
            converterViewModel = converterViewModel,
            modifier = Modifier
                .weight(0.35f)
                .padding(top = 12.dp)
        )

        ActionButtonsRow(
            converterViewModel = converterViewModel,
            modifier = Modifier.fillMaxWidth()
        )

        KeyboardSection(
            converterViewModel = converterViewModel,
            modifier = Modifier
                .weight(0.65f)
                .padding(bottom = 42.dp),
            contentAlignment = Alignment.BottomEnd
        )
    }
}

@Composable
private fun LandscapeLayout(
    converterViewModel: ConverterViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(0.6f)
                .padding(start = 16.dp)
        ) {
            TextFieldCard(
                converterViewModel = converterViewModel,
                modifier = Modifier.fillMaxWidth().weight(0.75f)
            )

            ActionButtonsRow(
                converterViewModel = converterViewModel,
                modifier = Modifier.fillMaxWidth().weight(0.25f)
            )
        }

        KeyboardSection(
            converterViewModel = converterViewModel,
            modifier = Modifier.weight(0.4f),
            contentAlignment = Alignment.Center,
            heightButtons = 46.dp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppNavBar(
    converterViewModel: ConverterViewModel
) {
    val currentRoute = converterViewModel.currentRoute.collectAsState().value
    val showMenu = remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "${currentRoute.name} Converter",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center
            )
        },
        actions = {
            IconButton(onClick = { showMenu.value = !showMenu.value }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu"
                )
            }

            DropdownMenu(
                expanded = showMenu.value,
                onDismissRequest = { showMenu.value = false }
            ) {
                Routes.allDestinations.forEach { route ->
                    DropdownMenuItem(
                        text = { Text(text = route.name) },
                        onClick = {
                            showMenu.value = false
                            converterViewModel.updateCurrentRoute(route)
                        },
                        leadingIcon = {
                            when (route) {
                                Routes.DestinationConverter -> {
                                    Icon(
                                        contentDescription = route.name,
                                        painter = painterResource(R.drawable.ic_triangular_ruler)
                                    )
                                }

                                Routes.TemperatureConverter -> {
                                    Icon(
                                        contentDescription = route.name,
                                        painter = painterResource(R.drawable.ic_temperature)
                                    )
                                }

                                Routes.WeightConverter -> {
                                    Icon(
                                        contentDescription = route.name,
                                        painter = painterResource(R.drawable.ic_weight)
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun TextFieldCard(
    converterViewModel: ConverterViewModel,
    modifier: Modifier = Modifier
) {
    val currentRoute = converterViewModel.currentRoute.collectAsState().value
    val focusedField = converterViewModel.focusedField.collectAsState().value
    val firstValue = converterViewModel.firstTextField.collectAsState().value
    val secondValue = converterViewModel.secondTextField.collectAsState().value
    val selectedMeasurementFirstField =
        converterViewModel.selectedMeasurementFirstField.collectAsState().value
    val selectedMeasurementSecondField =
        converterViewModel.selectedMeasurementSecondField.collectAsState().value

    Card(
        modifier = modifier.fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ConverterTextField(
                value = firstValue,
                selectedMeasurement = selectedMeasurementFirstField,
                availableUnits = when (currentRoute) {
                    Routes.DestinationConverter -> MeasurementUnit.Distance.allUnits
                    Routes.TemperatureConverter -> MeasurementUnit.Temperature.allUnits
                    Routes.WeightConverter -> MeasurementUnit.Weight.allUnits
                },
                onUnitSelected = {
                    converterViewModel.updateSelectedMeasurementFirstField(it)
                },
                isFocused = focusedField == 1,
                onClick = { converterViewModel.updateFocusedField(1) },
                modifier = Modifier.fillMaxWidth()
            )

            ConverterTextField(
                value = secondValue,
                selectedMeasurement = selectedMeasurementSecondField,
                availableUnits = when (currentRoute) {
                    Routes.DestinationConverter -> MeasurementUnit.Distance.allUnits
                    Routes.TemperatureConverter -> MeasurementUnit.Temperature.allUnits
                    Routes.WeightConverter -> MeasurementUnit.Weight.allUnits
                },
                onUnitSelected = {
                    converterViewModel.updateSelectedMeasurementSecondField(it)
                },
                isFocused = focusedField == 2,
                onClick = { converterViewModel.updateFocusedField(2) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ActionButtonsRow(
    converterViewModel: ConverterViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val localContext = LocalContext.current

    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionIconButton(
            text = "Copy results",
            icon = painterResource(R.drawable.ic_copy),
            onClick = {
                converterViewModel.copyValues(localContext)
            }
        )

        ActionIconButton(
            text = "Swap units",
            icon = painterResource(R.drawable.ic_swap),
            onClick = {
                converterViewModel.swapUnits()
            }
        )
    }
}

@Composable
private fun KeyboardSection(
    converterViewModel: ConverterViewModel,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment,
    heightButtons: Dp = 64.dp
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = contentAlignment
    ) {
        CalculatorKeyboard(
            onDigit = { digit -> converterViewModel.handleDigit(digit) },
            onDecimal = { converterViewModel.handleDecimal() },
            onSighChange = { converterViewModel.handleSignChange() },
            onBackspace = { converterViewModel.handleBackspace() },
            onClear = { converterViewModel.handleClear() },
            modifier = Modifier.fillMaxWidth(),
            heightButtons = heightButtons
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
fun PortraitLayoutPreview() {
    PortraitLayout(
        converterViewModel = ConverterViewModel()
    )
}

@Composable
@Preview(showBackground = true, widthDp = 640, heightDp = 360)
fun LandscapeLayoutPreview() {
    LandscapeLayout(
        converterViewModel = ConverterViewModel()
    )
}