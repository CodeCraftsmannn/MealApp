package com.example.mealapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// Everything the screens need to draw themselves
data class MealState(
    val meals: List<Meal> = sampleMeals
)

class MealViewModel : ViewModel() {

    // State lives here, so it survives screen rotation.
    // "private set" means only the ViewModel can change it.
    var state by mutableStateOf(MealState())
        private set

    // The single entry point for all actions
    fun onAction(action: MealAction) {
        if (action is MealAction.AddMeal) {
            addMeal(action.title, action.description)
        } else if (action is MealAction.ToggleFavorite) {
            toggleFavorite(action.id)
        }
    }

    private fun addMeal(title: String, description: String) {
        // Find the next free id
        val newId: Int
        if (state.meals.isEmpty()) {
            newId = 1
        } else {
            newId = state.meals.maxOf { it.id } + 1
        }

        val newMeal = Meal(
            id = newId,
            title = title,
            description = description,
            category = "Custom",
            area = "Unknown",
            rating = 0.0,
            emoji = "🍽️"
        )

        // Make a NEW list so Compose notices the change
        state = state.copy(meals = state.meals + newMeal)
    }

    private fun toggleFavorite(id: Int) {
        val newList = state.meals.map { meal ->
            if (meal.id == id) {
                meal.copy(isFavorite = !meal.isFavorite)
            } else {
                meal
            }
        }
        state = state.copy(meals = newList)
    }
}
