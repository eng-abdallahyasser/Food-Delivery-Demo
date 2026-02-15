package com.abdallahyasser.demoapp.data.relationship

import com.abdallahyasser.demoapp.data.model.User

// DEMO: Composition (items) and Association (customer, driver)
class Order(
    val id: String,
    // Association: Order HAS-A Customer (Customer can exist without this specific Order)
    val customer: User,
    
    // Composition: Order HAS-A list of OrderItems. 
    // Usually, these items are created specifically for this order.
    private val items: MutableList<OrderItem> = mutableListOf()
) {
    // Association: Order might have a Driver assigned later.
    var driver: User? = null

    fun addItem(item: OrderItem) {
        items.add(item)
    }

    fun getItems(): List<OrderItem> = items.toList()

    fun calculateTotal(): Double {
        return items.sumOf { it.totalPrice }
    }
}
