package com.example.mealapp

// All actions the UI can send up to the ViewModel
sealed class MealAction {
    // Add a new meal with a title and a description
    data class AddMeal(val title: String, val description: String) : MealAction()

    // Toggle the favorite flag of the meal with this id
    data class ToggleFavorite(val id: Int) : MealAction()
}
