package com.abdallahyasser.demoapp.data.relationship

// DEMO: Delegation - PaymentProcessor delegates the actual payment logic to a PaymentMethod.
interface PaymentMethod {
    fun processPayment(amount: Double)
}

class CreditCardPayment : PaymentMethod {
    override fun processPayment(amount: Double) {
        println("Paying $amount using Credit Card")
    }
}

class PayPalPayment : PaymentMethod {
    override fun processPayment(amount: Double) {
        println("Paying $amount using PayPal")
    }
}

class PaymentProcessor(private val method: PaymentMethod) : PaymentMethod by method {
    // The 'by method' syntax in Kotlin is a built-in way to implement Delegation.
    // It automatically delegates all interface methods to the 'method' instance.
}

// LECTURE: The instructor can explain how this promotes flexibility and separation of concerns.
