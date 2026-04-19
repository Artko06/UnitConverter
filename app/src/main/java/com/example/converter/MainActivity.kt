package com.example.converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.converter.ui.screen.ConverterScreen
import com.example.converter.ui.theme.ConverterTheme
import com.example.converter.ui.viewModel.ConverterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConverterTheme {
                val converterViewModel: ConverterViewModel = viewModel()

                ConverterScreen(
                    converterViewModel = converterViewModel
                )
            }
        }
    }
}