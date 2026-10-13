package com.example.mealapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {

    // The ViewModel is created here and survives rotation
    private val viewModel: MealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Only state and onAction go down, screens never see the ViewModel
                AppNavigation(
                    state = viewModel.state,
                    onAction = viewModel::onAction
                )
            }
        }
    }
}