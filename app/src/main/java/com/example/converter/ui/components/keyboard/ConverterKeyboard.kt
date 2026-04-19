package com.example.converter.ui.components.keyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorKeyboard(
    onDigit: (String) -> Unit,
    onDecimal: () -> Unit,
    onSighChange: () -> Unit,
    onBackspace: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
    heightButtons: Dp = 64.dp
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CalculatorButton(
                text = "7",
                onClick = { onDigit("7") },
                modifier = Modifier.weight(1f),
                height = heightButtons
            )
            CalculatorButton(
                text = "8",
                onClick = { onDigit("8") },
                modifier = Modifier.weight(1f),
                height = heightButtons
            )
            CalculatorButton(
                text = "9",
                onClick = { onDigit("9") },
                modifier = Modifier.weight(1f),
                height = heightButtons
            )
            CalculatorButton(
                text = "←",
                fontSize = 28.sp,
                onClick = onBackspace,
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.weight(1f),
                height = heightButtons
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CalculatorButton(
                text = "4",
                onClick = { onDigit("4") },
                modifier = Modifier.weight(1f),
                height = heightButtons
            )
            CalculatorButton(
                text = "5",
                onClick = { onDigit("5") },
                modifier = Modifier.weight(1f),
                height = heightButtons
            )
            CalculatorButton(
                text = "6",
                onClick = { onDigit("6") },
                modifier = Modifier.weight(1f),
                height = heightButtons
                )
            CalculatorButton(
                text = "C",
                onClick = onClear,
                backgroundColor = MaterialTheme.colorScheme.errorContainer,
                textColor = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.weight(1f),
                height = heightButtons
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CalculatorButton(
                text = "1",
                onClick = { onDigit("1") },
                modifier = Modifier.weight(1f),
                height = heightButtons
            )
            CalculatorButton(
                text = "2",
                onClick = { onDigit("2") },
                modifier = Modifier.weight(1f),
                height = heightButtons
            )
            CalculatorButton(
                text = "3",
                onClick = { onDigit("3") },
                modifier = Modifier.weight(1f),
                height = heightButtons
            )
            CalculatorButton(
                text = "±",
                onClick = { onSighChange() },
                modifier = Modifier.weight(1f),
                height = heightButtons
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CalculatorButton(
                text = "0",
                onClick = { onDigit("0") },
                modifier = Modifier.weight(1f),
                height = heightButtons
            )

            CalculatorButton(
                text = ".",
                fontSize = 26.sp,
                onClick = onDecimal,
                modifier = Modifier.weight(0.5f),
                height = heightButtons
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun SimpleCalculatorKeyboardPreview() {
    CalculatorKeyboard(
        onDigit = {},
        onBackspace = {},
        onClear = {},
        onDecimal = {},
        onSighChange = {}
    )
}