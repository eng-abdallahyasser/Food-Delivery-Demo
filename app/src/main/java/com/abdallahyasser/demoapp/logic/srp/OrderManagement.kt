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
        
        // Email logic
        println("Sending confirmation email...")
        
        // Logging logic
        println("Logging order ${order.id} to system...")
    }
}

// DEMO: SRP Solution - Each class has ONE reason to change.

// ===== VALIDATION RESPONSIBILITY =====
class OrderValidator {
    fun validate(order: Order): Boolean {
        return order.getItems().isNotEmpty()
    }
    
    fun validateMinimumAmount(order: Order, minAmount: Double): Boolean {
        val total = order.calculateTotal()
        if (total < minAmount) {
            println("⚠️ Order total ($total) is below minimum ($minAmount)")
            return false
        }
        return true
    }
    
    fun validateDeliveryAddress(order: Order): Boolean {
        // In real app, would check if address is valid and in delivery zone
        val hasAddress = order.customer.email.isNotEmpty() // Simplified check
        if (!hasAddress) {
            println("⚠️ No delivery address provided")
            return false
        }
        return true
    }
    
    fun validatePaymentMethod(order: Order): Boolean {
        // In real app, would verify payment method is valid
        println("✅ Payment method validated")
        return true
    }
    
    fun validateAll(order: Order, minAmount: Double = 10.0): Boolean {
        return validate(order) && 
               validateMinimumAmount(order, minAmount) &&
               validateDeliveryAddress(order) &&
               validatePaymentMethod(order)
    }
}

// ===== PERSISTENCE RESPONSIBILITY =====
class OrderRepository {
    fun save(order: Order) {
        println("💾 Saving order ${order.id} to Database...")
    }
    
    fun update(order: Order) {
        println("✏️ Updating order ${order.id} in Database...")
    }
    
    fun delete(orderId: String) {
        println("🗑️ Deleting order $orderId from Database...")
    }
}

// ===== CALCULATION RESPONSIBILITY =====
class OrderPriceCalculator {
    private val TAX_RATE = 0.15 // 15% tax
    private val DELIVERY_FEE = 5.0
    
    fun calculate(order: Order): Double {
        return order.calculateTotal()
    }
    
    fun calculateWithTax(order: Order): Double {
        val subtotal = calculate(order)
        val tax = subtotal * TAX_RATE
        println("💰 Subtotal: $$subtotal | Tax (15%): $${"%.2f".format(tax)}")
        return subtotal + tax
    }
    
    fun calculateWithDelivery(order: Order): Double {
        val subtotal = calculate(order)
        println("💰 Subtotal: $$subtotal | Delivery: $$DELIVERY_FEE")
        return subtotal + DELIVERY_FEE
    }
    
    fun calculateFinalTotal(order: Order): Double {
        val subtotal = calculate(order)
        val tax = subtotal * TAX_RATE
        val total = subtotal + tax + DELIVERY_FEE
        
        println("💰 Order Breakdown:")
        println("   Subtotal: $${"%.2f".format(subtotal)}")
        println("   Tax (15%): $${"%.2f".format(tax)}")
        println("   Delivery: $$DELIVERY_FEE")
        println("   TOTAL: $${"%.2f".format(total)}")
        
        return total
    }
}

// ===== LOGGING RESPONSIBILITY =====
class OrderLogger {
    fun logOrderCreated(order: Order) {
        println("📝 [LOG] Order ${order.id} created at ${System.currentTimeMillis()}")
    }
    
    fun logOrderUpdated(order: Order) {
        println("📝 [LOG] Order ${order.id} updated at ${System.currentTimeMillis()}")
    }
    
    fun logOrderCompleted(order: Order) {
        println("📝 [LOG] Order ${order.id} completed at ${System.currentTimeMillis()}")
    }
    
    fun logOrderCancelled(orderId: String, reason: String) {
        println("📝 [LOG] Order $orderId cancelled. Reason: $reason")
    }
    
    fun logError(orderId: String, error: String) {
        println("📝 [ERROR] Order $orderId - $error")
    }
}

// ===== NOTIFICATION RESPONSIBILITY =====
class OrderEmailNotifier {
    fun sendOrderConfirmation(order: Order) {
        println("📧 Sending order confirmation email to ${order.customer.email}")
        println("   Subject: Order ${order.id} Confirmed!")
        println("   Body: Thank you for your order of $${order.calculateTotal()}")
    }
    
    fun sendOrderShipped(order: Order, trackingNumber: String) {
        println("📧 Sending shipment notification to ${order.customer.email}")
        println("   Subject: Your order is on the way!")
        println("   Tracking: $trackingNumber")
    }
    
    fun sendOrderDelivered(order: Order) {
        println("📧 Sending delivery confirmation to ${order.customer.email}")
        println("   Subject: Order ${order.id} Delivered!")
    }
    
    fun sendOrderCancelled(order: Order, reason: String) {
        println("📧 Sending cancellation email to ${order.customer.email}")
        println("   Subject: Order ${order.id} Cancelled")
        println("   Reason: $reason")
    }
}

// ===== ORCHESTRATOR (Uses all SRP classes) =====
class GoodOrderManager(
    private val validator: OrderValidator,
    private val repository: OrderRepository,
    private val calculator: OrderPriceCalculator,
    private val logger: OrderLogger,
    private val notifier: OrderEmailNotifier
) {
    fun processOrder(order: Order) {
        println("\n🎯 Processing Order ${order.id}...\n")
        
        // Each responsibility is handled by a dedicated class
        if (!validator.validateAll(order)) {
            logger.logError(order.id, "Validation failed")
            throw Exception("Order validation failed")
        }
        
        val total = calculator.calculateFinalTotal(order)
        
        repository.save(order)
        
        logger.logOrderCreated(order)
        
        notifier.sendOrderConfirmation(order)
        
        println("\n✅ Order ${order.id} processed successfully! Total: $${"%.2f".format(total)}\n")
    }
}

// DEMO: Now if we need to change HOW we calculate prices, we only touch OrderPriceCalculator.
// If we need to change HOW we send emails, we only touch OrderEmailNotifier.
// Each class has ONE reason to change!
