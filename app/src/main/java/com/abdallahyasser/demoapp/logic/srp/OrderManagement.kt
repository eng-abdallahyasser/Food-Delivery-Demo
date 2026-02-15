package com.abdallahyasser.demoapp.logic.srp

import com.abdallahyasser.demoapp.data.relationship.Order

// LECTURE: SRP Violation - This class does too many things.
// It manages the order, validates it, calculates price, AND saves it to the DB.
class BadOrderManager {
    fun processOrder(order: Order) {
        // Validation logic
        if (order.getItems().isEmpty()) {
            throw Exception("Order is empty")
        }

        // Calculation logic
        val total = order.calculateTotal()
        println("Total is $total")

        // Database logic
        println("Saving order ${order.id} to database...")
    }
}

// DEMO: SRP Solution - Each class has one reason to change.

class OrderValidator {
    fun validate(order: Order): Boolean {
        return order.getItems().isNotEmpty()
    }
}

class OrderRepository {
    fun save(order: Order) {
        println("Saving order ${order.id} to Database...")
    }
}

class OrderPriceCalculator {
    fun calculate(order: Order): Double {
        return order.calculateTotal()
    }
}

// TODO: Ask students to implement an OrderLogger class to handle logging separately.
