package com.abdallahyasser.demoapp.data.relationship

import com.abdallahyasser.demoapp.data.model.FoodItem

// DEMO: Composition - OrderItem is part of an Order.
// In a true composition, the OrderItem's lifecycle is tied to the Order.
data class OrderItem(
    val foodItem: FoodItem,
    val quantity: Int
) {
    val totalPrice: Double get() = foodItem.price * quantity
}
