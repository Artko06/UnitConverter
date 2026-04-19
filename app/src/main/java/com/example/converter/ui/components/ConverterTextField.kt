package com.example.converter.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.converter.ui.model.MeasurementUnit

@Composable
fun ConverterTextField(
    value: String,
    selectedMeasurement: MeasurementUnit,
    availableUnits: List<MeasurementUnit>,
    onUnitSelected: (MeasurementUnit) -> Unit,
    isFocused: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val showUnits = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(value) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(26.dp))
            .then(
                if (isFocused) {
                    Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(26.dp)
                    )
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .heightIn(min = 36.dp)
                    .weight(weight = 1f)
                    .horizontalScroll(scrollState),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = value,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    softWrap = false,
                    maxLines = 1,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier.width(42.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.clickable(
                        onClick = { showUnits.value = !showUnits.value }
                    )
                        .padding(end = 8.dp)
                    ,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = selectedMeasurement.symbol,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select unit",
                        modifier = Modifier.size(16.dp)
                    )
                }

                DropdownMenu(
                    expanded = showUnits.value,
                    onDismissRequest = { showUnits.value = false }
                ) {
                    availableUnits.forEach { unit ->
                        DropdownMenuItem(
                            text = {
                                Text(text = unit.symbol)
                            },
                            onClick = {
                                showUnits.value = false
                                onUnitSelected(unit)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ConverterTextFieldPreview() {
    ConverterTextField(
        value = "1003434340",
        selectedMeasurement = MeasurementUnit.Temperature.Kelvin,
        availableUnits = emptyList(),
        onUnitSelected = {},
        isFocused = true,
        onClick = {}
    )
}