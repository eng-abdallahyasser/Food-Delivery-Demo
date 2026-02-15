package com.abdallahyasser.demoapp.data.relationship

import com.abdallahyasser.demoapp.data.model.FoodItem

// DEMO: Aggregation - A Menu "has" FoodItems, but FoodItems can exist on their own.
class Menu(
    val id: String,
    val name: String,
    // Aggregation: The menu contains references to items, but doesn't "own" their lifecycle.
    val items: List<FoodItem>
)
