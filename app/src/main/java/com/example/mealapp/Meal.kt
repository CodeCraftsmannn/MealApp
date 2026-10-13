package com.example.mealapp

// One meal item (fields are similar to the TheMealDB JSON)
data class Meal(
    val id: Int,                      // unique id
    val title: String,                // meal name
    val description: String,          // short description
    val category: String,             // topic field 1
    val area: String,                 // topic field 2 (country)
    val rating: Double,               // topic field 3
    val emoji: String,                // used as the "cover" on the card
    val isFavorite: Boolean = false   // changed by the toggle action
)

// Sample data (later it will come from the API)
val sampleMeals = listOf(
    Meal(1, "Spaghetti Carbonara", "Pasta with eggs, cheese and pancetta.", "Pasta", "Italian", 4.7, "🍝"),
    Meal(2, "Chicken Curry", "Chicken cooked in a spicy sauce.", "Chicken", "Indian", 4.5, "🍛"),
    Meal(3, "Sushi Rolls", "Rice and fish wrapped in seaweed.", "Seafood", "Japanese", 4.6, "🍣"),
    Meal(4, "Beef Burger", "Juicy beef patty in a soft bun.", "Beef", "American", 4.2, "🍔"),
    Meal(5, "Greek Salad", "Fresh vegetables with feta cheese.", "Vegetarian", "Greek", 4.0, "🥗"),
    Meal(6, "Tacos", "Corn tortillas with meat and salsa.", "Beef", "Mexican", 4.4, "🌮")
)