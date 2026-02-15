package com.abdallahyasser.demoapp.logic.ocp

// DEMO: OCP Solution - Open for extension, Closed for modification.
// We can add new discount types without changing the DiscountCalculator.

interface DiscountProvider {
    fun applyDiscount(amount: Double): Double
}

class PercentageDiscount(val percentage: Double) : DiscountProvider {
    override fun applyDiscount(amount: Double): Double {
        return amount * (1 - percentage / 100)
    }
}

class FixedAmountDiscount(val discountAmount: Double) : DiscountProvider {
    override fun applyDiscount(amount: Double): Double {
        return (amount - discountAmount).coerceAtLeast(0.0)
    }
}

// LECTURE: If we need a "Buy 1 Get 1 Free" discount, we just implement DiscountProvider
// rather than adding an 'if' / 'when' block to a central class.
class DiscountCalculator(private val provider: DiscountProvider) {
    fun calculateTotal(amount: Double): Double {
        return provider.applyDiscount(amount)
    }
}

// TODO: Ask students to implement a 'NoDiscount' class that returns the original amount.
