package com.abdallahyasser.demoapp.logic.ocp

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Month

// DEMO: OCP Solution - Open for extension, Closed for modification.
// We can add new discount types without changing the DiscountCalculator.

interface DiscountProvider {
    fun applyDiscount(amount: Double): Double
    fun getDiscountName(): String
}

// ===== BASIC DISCOUNTS =====

class NoDiscount : DiscountProvider {
    override fun applyDiscount(amount: Double): Double {
        return amount
    }
    
    override fun getDiscountName(): String = "No Discount"
}

class PercentageDiscount(private val percentage: Double) : DiscountProvider {
    override fun applyDiscount(amount: Double): Double {
        val discount = amount * (percentage / 100)
        println("💸 ${getDiscountName()}: -$${"%.2f".format(discount)}")
        return amount * (1 - percentage / 100)
    }
    
    override fun getDiscountName(): String = "$percentage% Off"
}

class FixedAmountDiscount(private val discountAmount: Double) : DiscountProvider {
    override fun applyDiscount(amount: Double): Double {
        val actualDiscount = minOf(discountAmount, amount)
        println("💸 ${getDiscountName()}: -$${"%.2f".format(actualDiscount)}")
        return (amount - discountAmount).coerceAtLeast(0.0)
    }
    
    override fun getDiscountName(): String = "$$discountAmount Off"
}

// ===== ADVANCED DISCOUNTS =====

class BuyOneGetOneFreeDiscount(private val itemPrice: Double) : DiscountProvider {
    override fun applyDiscount(amount: Double): Double {
        // Calculate how many items at this price
        val itemCount = (amount / itemPrice).toInt()
        val freeItems = itemCount / 2
        val discount = freeItems * itemPrice
        
        println("💸 ${getDiscountName()}: $freeItems free items = -$${"%.2f".format(discount)}")
        return amount - discount
    }
    
    override fun getDiscountName(): String = "Buy 1 Get 1 Free"
}

class SeasonalDiscount(
    private val percentage: Double,
    private val startMonth: Month,
    private val endMonth: Month
) : DiscountProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun applyDiscount(amount: Double): Double {
        val currentMonth = LocalDate.now().month
        val isInSeason = currentMonth.ordinal in startMonth.ordinal..endMonth.ordinal

        
        return if (isInSeason) {
            val discount = amount * (percentage / 100)
            println("💸 ${getDiscountName()}: -$${"%.2f".format(discount)} (Seasonal!)")
            amount * (1 - percentage / 100)
        } else {
            println("ℹ️ Seasonal discount not active (${startMonth.name} - ${endMonth.name})")
            amount
        }
    }
    
    override fun getDiscountName(): String = "Seasonal $percentage% Off"
}

class LoyaltyPointsDiscount(
    private val pointsAvailable: Int,
    private val pointsToMoneyRatio: Double = 0.01 // 1 point = $0.01
) : DiscountProvider {
    override fun applyDiscount(amount: Double): Double {
        val maxDiscount = pointsAvailable * pointsToMoneyRatio
        val actualDiscount = minOf(maxDiscount, amount)
        val pointsUsed = (actualDiscount / pointsToMoneyRatio).toInt()
        
        println("💸 ${getDiscountName()}: -$${"%.2f".format(actualDiscount)} ($pointsUsed points)")
        return amount - actualDiscount
    }
    
    override fun getDiscountName(): String = "Loyalty Points ($pointsAvailable pts)"
}

class CouponDiscount(
    private val couponCode: String,
    private val percentage: Double,
    private val validCodes: Set<String> = setOf("SAVE10", "WELCOME20", "VIP30")
) : DiscountProvider {
    override fun applyDiscount(amount: Double): Double {
        return if (couponCode in validCodes) {
            val discount = amount * (percentage / 100)
            println("💸 ${getDiscountName()}: -$${"%.2f".format(discount)} ✅")
            amount * (1 - percentage / 100)
        } else {
            println("❌ Invalid coupon code: $couponCode")
            amount
        }
    }
    
    override fun getDiscountName(): String = "Coupon '$couponCode' ($percentage% off)"
}

// ===== DISCOUNT CALCULATOR =====

// LECTURE: If we need a new discount type, we just implement DiscountProvider
// rather than adding an 'if' / 'when' block to this class.
class DiscountCalculator(private val provider: DiscountProvider) {
    fun calculateTotal(amount: Double): Double {
        println("\n💰 Original Amount: $${"%.2f".format(amount)}")
        println("🎁 Applying: ${provider.getDiscountName()}")
        val finalAmount = provider.applyDiscount(amount)
        println("✅ Final Amount: $${"%.2f".format(finalAmount)}\n")
        return finalAmount
    }
}

// ===== STACKED DISCOUNTS =====

class StackedDiscounts(private val discounts: List<DiscountProvider>) : DiscountProvider {
    override fun applyDiscount(amount: Double): Double {
        var currentAmount = amount
        println("📚 Applying ${discounts.size} stacked discounts:")
        
        discounts.forEach { discount ->
            currentAmount = discount.applyDiscount(currentAmount)
        }
        
        val totalSaved = amount - currentAmount
        println("💰 Total Saved: $${"%.2f".format(totalSaved)}")
        return currentAmount
    }
    
    override fun getDiscountName(): String = "Stacked Discounts (${discounts.size})"
}

// DEMO: Examples of using different discount strategies
fun demonstrateOCP() {
    println("=== OCP Demonstration ===\n")
    
    val originalPrice = 100.0
    
    // Different discount strategies
    val noDiscount = DiscountCalculator(NoDiscount())
    val percentOff = DiscountCalculator(PercentageDiscount(20.0))
    val fixedOff = DiscountCalculator(FixedAmountDiscount(15.0))
    val bogo = DiscountCalculator(BuyOneGetOneFreeDiscount(25.0))
    val seasonal = DiscountCalculator(SeasonalDiscount(25.0, Month.DECEMBER, Month.JANUARY))
    val loyalty = DiscountCalculator(LoyaltyPointsDiscount(500))
    val coupon = DiscountCalculator(CouponDiscount("SAVE10", 10.0))
    
    // Stacked discounts
    val stacked = DiscountCalculator(
        StackedDiscounts(
            listOf(
                PercentageDiscount(10.0),
                FixedAmountDiscount(5.0),
                LoyaltyPointsDiscount(200)
            )
        )
    )
    
    println("✨ All discount types work with the same DiscountCalculator!")
    println("✨ We can add new discount types without modifying existing code!")
}
