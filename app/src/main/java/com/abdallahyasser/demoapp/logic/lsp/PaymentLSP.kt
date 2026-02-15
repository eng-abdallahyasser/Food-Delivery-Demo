package com.abdallahyasser.demoapp.logic.lsp

// LECTURE: LSP - Subtypes must be substitutable for their base types.

interface PaymentMethod {
    fun pay(amount: Double)
}

class BankTransfer : PaymentMethod {
    override fun pay(amount: Double) {
        println("Processing Bank Transfer of $amount")
        // Logic for bank transfer...
    }
}

// DEMO: This is a safe substitution.
class CryptoPayment : PaymentMethod {
    override fun pay(amount: Double) {
        println("Processing Crypto Payment of $amount")
        // Logic for crypto...
    }
}

// LECTURE: A BAD example would be a Payment method that throws UnsupportedOperationException
// for certain valid amounts, breaking the contract of the base type.
class CashOnDelivery : PaymentMethod {
    override fun pay(amount: Double) {
        println("Marked as Cash on Delivery for $amount")
    }
}

class CheckoutService {
    fun finalizePayment(method: PaymentMethod, amount: Double) {
        // Any PaymentMethod should work here without the caller needing to know the details.
        method.pay(amount)
    }
}
