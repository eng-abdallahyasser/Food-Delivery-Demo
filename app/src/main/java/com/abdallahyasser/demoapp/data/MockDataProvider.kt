package com.abdallahyasser.demoapp.data

import com.abdallahyasser.demoapp.data.model.FoodItem
import com.abdallahyasser.demoapp.data.relationship.Menu
import com.abdallahyasser.demoapp.data.relationship.Restaurant

// DEMO: A static data source to provide initial data for the application.
object MockDataProvider {

    val foodItems = listOf(
        FoodItem("f1", "Classic Burger", "Juicy beef patty with lettuce and tomato", 10.99),
        FoodItem("f2", "Double Cheeseburger", "Two beef patties with double cheese", 13.99),
        FoodItem("f3", "French Fries", "Crispy salted fries", 3.99),
        FoodItem("f4", "Margherita Pizza", "Fresh mozzarella and basil", 12.99),
        FoodItem("f5", "Pepperoni Pizza", "Classic pepperoni and cheese", 14.99),
        FoodItem("f6", "Caesar Salad", "Fresh romaine with caesar dressing", 8.99)
    )

    val menus = listOf(
        Menu("m1", "Burger Joint Menu", foodItems.subList(0, 3)),
        Menu("m2", "Pizza Palace Menu", foodItems.subList(3, 5)),
        Menu("m3", "Healthy Hub Menu", foodItems.subList(5, 6))
    )

    val restaurants = listOf(
        Restaurant("r1", "Big Burger Joint", menus[0]),
        Restaurant("r2", "Amazing Pizza Palace", menus[1]),
        Restaurant("r3", "The Healthy Hub", menus[2])
    )

    fun getRestaurantById(id: String): Restaurant? {
        return restaurants.find { it.id == id }
    }

    fun getAllFoodItems(): List<FoodItem> = foodItems
}
