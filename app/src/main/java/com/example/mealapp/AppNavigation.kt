package com.example.mealapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// Navigation gets only state + onAction. It never sees the ViewModel.
@Composable
fun AppNavigation(
    state: MealState,
    onAction: (MealAction) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {

        // Route 1: the list
        composable("list") {
            ListScreen(
                state = state,
                onAction = onAction,
                onMealClick = { id -> navController.navigate("details/" + id) },
                onAddClick = { navController.navigate("add") }
            )
        }

        // Route 2: details with a parameter
        composable(
            route = "details/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("itemId") ?: -1
            DetailsScreen(
                state = state,
                mealId = id,
                onBack = { navController.popBackStack() }
            )
        }

        // Bonus route: add screen
        composable("add") {
            AddScreen(
                onAction = onAction,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
