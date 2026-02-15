package com.abdallahyasser.demoapp.ui

import com.abdallahyasser.demoapp.data.model.FoodItem
import com.abdallahyasser.demoapp.data.model.User
import com.abdallahyasser.demoapp.data.relationship.*
import com.abdallahyasser.demoapp.logic.dip.FirebaseOrderRepository
import com.abdallahyasser.demoapp.logic.dip.OrderService
import com.abdallahyasser.demoapp.logic.ocp.PercentageDiscount

class DemoLauncher {
    fun runDemo(): String {
        val result = StringBuilder()

        // 1. Relationships Demo
        val customer = User("1", "John Doe", "john@example.com", "123456")
        val pizza = FoodItem("p1", "Margherita Pizza", "Classic cheese and tomato", 15.0)
        
        // Composition
        val order = Order("ord-101", customer)
        order.addItem(OrderItem(pizza, 2))
        
        result.append("Order Total: ${order.calculateTotal()}\n")

        // Aggregation
        val menu = Menu("m1", "Italian Menu", listOf(pizza))
        val restaurant = Restaurant("r1", "Pizza Palace", menu)
        result.append("Restaurant: ${restaurant.name} has ${restaurant.menu?.items?.size ?: 0} items\n")

        // 2. SOLID Demo (DIP + OCP)
        val discount = PercentageDiscount(10.0) // OCP
        val finalPrice = discount.applyDiscount(order.calculateTotal())
        result.append("Price after 10% discount: $finalPrice\n")

        val repo = FirebaseOrderRepository() // DIP implementation
        val orderService = OrderService(repo)
        orderService.finishOrder(order)
        result.append("Order finished using DIP Repository.\n\n")

        // 3. Static Data Demo
        result.append("--- Static Data (MockDataProvider) ---\n")
        val restaurants = com.abdallahyasser.demoapp.data.MockDataProvider.restaurants
        restaurants.forEach { rest ->
            result.append("Restaurant: ${rest.name}\n")
            rest.menu?.items?.forEach { item ->
                result.append("  - ${item.name} ($${item.price})\n")
            }
        }

        return result.toString()
    }
}
