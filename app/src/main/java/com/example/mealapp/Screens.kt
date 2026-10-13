package com.example.mealapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------- LIST SCREEN ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    state: MealState,                  // the screen gets only state...
    onAction: (MealAction) -> Unit,    // ...and actions
    onMealClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("My Meals") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        // LazyColumn only draws the visible items
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.meals, key = { it.id }) { meal ->
                MealCard(
                    meal = meal,
                    onClick = { onMealClick(meal.id) },
                    onFavoriteClick = { onAction(MealAction.ToggleFavorite(meal.id)) }
                )
            }
        }
    }
}

// ---------- ITEM CARD ----------
@Composable
fun MealCard(
    meal: Meal,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Box = the "cover": emoji in the middle of a colored circle
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(meal.emoji, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Texts take all the free space in the middle
            Column(modifier = Modifier.weight(1f)) {
                Text(meal.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(meal.category + " • " + meal.area, fontSize = 13.sp)
                Text("⭐ " + meal.rating, fontSize = 13.sp)
            }

            // Heart icon: tapping it sends an action up
            val heart: String
            if (meal.isFavorite) {
                heart = "❤️"
            } else {
                heart = "🤍"
            }
            Text(
                text = heart,
                fontSize = 22.sp,
                modifier = Modifier.clickable { onFavoriteClick() }.padding(8.dp)
            )

            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Open")
        }
    }
}

// ---------- DETAILS SCREEN ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    state: MealState,
    mealId: Int,
    onBack: () -> Unit
) {
    // Find the meal that matches the id from the route
    val meal = state.meals.firstOrNull { it.id == mealId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (meal == null) {
            Text("Meal not found", modifier = Modifier.padding(padding).padding(16.dp))
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(meal.emoji, fontSize = 80.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(meal.title, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(meal.category + " • " + meal.area)
                Text("⭐ " + meal.rating)
                Spacer(modifier = Modifier.height(16.dp))
                Text(meal.description, fontSize = 16.sp)
            }
        }
    }
}

// ---------- ADD SCREEN (bonus) ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    onAction: (MealAction) -> Unit,
    onBack: () -> Unit
) {
    // Text being typed is temporary, so remember is fine here
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Meal") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    onAction(MealAction.AddMeal(title, description))
                    onBack()
                },
                enabled = title.isNotBlank(),   // disabled while the title is blank
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}