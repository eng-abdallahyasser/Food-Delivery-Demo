package com.abdallahyasser.demoapp.logic.dip

import com.abdallahyasser.demoapp.data.relationship.Order

// LECTURE: DIP - High-level modules should not depend on low-level modules. Both should depend on abstractions.

// Abstraction (Interface)
interface IOrderRepository {
    fun saveOrder(order: Order)
}

// Low-level module
class FirebaseOrderRepository : IOrderRepository {
    override fun saveOrder(order: Order) {
        println("Saving order to Firebase...")
    }
}

// Low-level module
class RoomOrderRepository : IOrderRepository {
    override fun saveOrder(order: Order) {
        println("Saving order to Local SQL Database...")
    }
}

// High-level module
// DEMO: This class doesn't care WHERE the order is saved, just THAT it is saved.
class OrderService(private val repository: IOrderRepository) {
    fun finishOrder(order: Order) {
        // Business logic...
        repository.saveOrder(order)
    }
}

// TODO: Ask students to implement a 'MockOrderRepository' for unit testing.
