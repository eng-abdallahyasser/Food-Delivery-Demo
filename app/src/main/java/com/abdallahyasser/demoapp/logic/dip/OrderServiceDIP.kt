package com.abdallahyasser.demoapp.logic.dip

import com.abdallahyasser.demoapp.data.relationship.Order

// LECTURE: DIP - High-level modules should not depend on low-level modules. Both should depend on abstractions.

// Abstraction (Interface) - Expanded with realistic CRUD operations
interface IOrderRepository {
    fun saveOrder(order: Order)
    fun getOrderById(id: String): Order?
    fun getAllOrders(): List<Order>
    fun updateOrder(order: Order)
    fun deleteOrder(id: String)
    fun getOrdersByCustomerId(customerId: String): List<Order>
}

// Low-level module - Firebase Implementation
class FirebaseOrderRepository : IOrderRepository {
    private val orders = mutableMapOf<String, Order>()

    override fun saveOrder(order: Order) {
        println("💾 Saving order ${order.id} to Firebase Cloud...")
        orders[order.id] = order
    }

    override fun getOrderById(id: String): Order? {
        println("🔍 Fetching order $id from Firebase...")
        return orders[id]
    }

    override fun getAllOrders(): List<Order> {
        println("📋 Fetching all orders from Firebase...")
        return orders.values.toList()
    }

    override fun updateOrder(order: Order) {
        println("✏️ Updating order ${order.id} in Firebase...")
        orders[order.id] = order
    }

    override fun deleteOrder(id: String) {
        println("🗑️ Deleting order $id from Firebase...")
        orders.remove(id)
    }

    override fun getOrdersByCustomerId(customerId: String): List<Order> {
        println("👤 Fetching orders for customer $customerId from Firebase...")
        return orders.values.filter { it.customer.id == customerId }
    }
}

// Low-level module - Local Database Implementation
class RoomOrderRepository : IOrderRepository {
    private val localOrders = mutableListOf<Order>()

    override fun saveOrder(order: Order) {
        println("💾 Saving order ${order.id} to Local SQL Database...")
        localOrders.add(order)
    }

    override fun getOrderById(id: String): Order? {
        println("🔍 Querying order $id from Local Database...")
        return localOrders.find { it.id == id }
    }

    override fun getAllOrders(): List<Order> {
        println("📋 SELECT * FROM orders...")
        return localOrders.toList()
    }

    override fun updateOrder(order: Order) {
        println("✏️ UPDATE orders SET... WHERE id = ${order.id}")
        val index = localOrders.indexOfFirst { it.id == order.id }
        if (index != -1) {
            localOrders[index] = order
        }
    }

    override fun deleteOrder(id: String) {
        println("🗑️ DELETE FROM orders WHERE id = $id")
        localOrders.removeIf { it.id == id }
    }

    override fun getOrdersByCustomerId(customerId: String): List<Order> {
        println("👤 SELECT * FROM orders WHERE customer_id = $customerId")
        return localOrders.filter { it.customer.id == customerId }
    }
}

// Low-level module - REST API Implementation
class ApiOrderRepository : IOrderRepository {
    override fun saveOrder(order: Order) {
        println("🌐 POST /api/orders - Sending order ${order.id} to REST API...")
    }

    override fun getOrderById(id: String): Order? {
        println("🌐 GET /api/orders/$id - Fetching from REST API...")
        return null // Would return parsed JSON response
    }

    override fun getAllOrders(): List<Order> {
        println("🌐 GET /api/orders - Fetching all orders from REST API...")
        return emptyList() // Would return parsed JSON array
    }

    override fun updateOrder(order: Order) {
        println("🌐 PUT /api/orders/${order.id} - Updating via REST API...")
    }

    override fun deleteOrder(id: String) {
        println("🌐 DELETE /api/orders/$id - Removing via REST API...")
    }

    override fun getOrdersByCustomerId(customerId: String): List<Order> {
        println("🌐 GET /api/customers/$customerId/orders - Fetching from REST API...")
        return emptyList()
    }
}

// Low-level module - Mock Repository for Unit Testing
class MockOrderRepository : IOrderRepository {
    val savedOrders = mutableListOf<Order>()
    var shouldFailOnSave = false

    override fun saveOrder(order: Order) {
        if (shouldFailOnSave) {
            throw Exception("Mock failure: Unable to save order")
        }
        println("🧪 Mock: Saving order ${order.id} (for testing)")
        savedOrders.add(order)
    }

    override fun getOrderById(id: String): Order? {
        println("🧪 Mock: Getting order $id (for testing)")
        return savedOrders.find { it.id == id }
    }

    override fun getAllOrders(): List<Order> {
        println("🧪 Mock: Getting all orders (for testing)")
        return savedOrders.toList()
    }

    override fun updateOrder(order: Order) {
        println("🧪 Mock: Updating order ${order.id} (for testing)")
        val index = savedOrders.indexOfFirst { it.id == order.id }
        if (index != -1) {
            savedOrders[index] = order
        }
    }

    override fun deleteOrder(id: String) {
        println("🧪 Mock: Deleting order $id (for testing)")
        savedOrders.removeIf { it.id == id }
    }

    override fun getOrdersByCustomerId(customerId: String): List<Order> {
        println("🧪 Mock: Getting orders for customer $customerId (for testing)")
        return savedOrders.filter { it.customer.id == customerId }
    }
}

// High-level module - Enhanced with more business logic
// DEMO: This class doesn't care WHERE the order is saved, just THAT it is saved.
class OrderService(private val repository: IOrderRepository) {
    
    fun finishOrder(order: Order) {
        println("\n📦 Processing order ${order.id}...")
        // Business logic: validate, calculate, then save
        repository.saveOrder(order)
        println("✅ Order ${order.id} completed!\n")
    }

    fun getOrderDetails(orderId: String): Order? {
        println("\n🔎 Looking up order $orderId...")
        return repository.getOrderById(orderId)
    }

    fun cancelOrder(orderId: String) {
        println("\n❌ Cancelling order $orderId...")
        repository.deleteOrder(orderId)
        println("✅ Order cancelled!\n")
    }

    fun updateOrderStatus(order: Order) {
        println("\n🔄 Updating order ${order.id}...")
        repository.updateOrder(order)
        println("✅ Order updated!\n")
    }

    fun getCustomerOrderHistory(customerId: String): List<Order> {
        println("\n📜 Fetching order history for customer $customerId...")
        val orders = repository.getOrdersByCustomerId(customerId)
        println("✅ Found ${orders.size} orders\n")
        return orders
    }

    fun getAllActiveOrders(): List<Order> {
        println("\n📊 Fetching all active orders...")
        return repository.getAllOrders()
    }
}

// DEMO: We can easily swap implementations without changing OrderService
fun demonstrateDIP() {
    println("=== DIP Demonstration ===\n")
    
    // Using Firebase
    val firebaseRepo = FirebaseOrderRepository()
    val serviceWithFirebase = OrderService(firebaseRepo)
    
    // Using Local Database
    val roomRepo = RoomOrderRepository()
    val serviceWithRoom = OrderService(roomRepo)
    
    // Using Mock for Testing
    val mockRepo = MockOrderRepository()
    val serviceWithMock = OrderService(mockRepo)
    
    println("✨ All services work identically, regardless of the underlying storage!")
}
