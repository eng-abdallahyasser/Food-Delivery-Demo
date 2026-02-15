package com.abdallahyasser.demoapp.logic.lsp

// LECTURE: LSP - Subtypes must be substitutable for their base types.
// Any implementation of PaymentMethod should work correctly without breaking the contract.

// ===== PAYMENT RESULT =====

sealed class PaymentResult {
    data class Success(val transactionId: String, val message: String) : PaymentResult()
    data class Failure(val errorCode: String, val message: String) : PaymentResult()
}

// ===== PAYMENT METHOD INTERFACE =====

interface PaymentMethod {
    fun pay(amount: Double): PaymentResult
    fun refund(transactionId: String, amount: Double): PaymentResult
    fun getPaymentMethodName(): String
}

// ===== VALID LSP IMPLEMENTATIONS =====

class CreditCardPayment(
    private val cardNumber: String,
    private val cvv: String
) : PaymentMethod {
    override fun pay(amount: Double): PaymentResult {
        println("💳 Processing Credit Card payment...")
        
        // Validate card
        if (cardNumber.length != 16) {
            return PaymentResult.Failure("INVALID_CARD", "Invalid card number")
        }
        
        if (amount <= 0) {
            return PaymentResult.Failure("INVALID_AMOUNT", "Amount must be positive")
        }
        
        val transactionId = "CC-${System.currentTimeMillis()}"
        println("✅ Credit Card payment of $$amount successful!")
        return PaymentResult.Success(transactionId, "Payment processed via ${getPaymentMethodName()}")
    }
    
    override fun refund(transactionId: String, amount: Double): PaymentResult {
        println("💳 Processing Credit Card refund...")
        println("✅ Refunded $$amount to card ending in ${cardNumber.takeLast(4)}")
        return PaymentResult.Success(transactionId, "Refund processed")
    }
    
    override fun getPaymentMethodName(): String = "Credit Card (****${cardNumber.takeLast(4)})"
}

class BankTransfer(private val accountNumber: String) : PaymentMethod {
    override fun pay(amount: Double): PaymentResult {
        println("🏦 Processing Bank Transfer...")
        
        if (amount <= 0) {
            return PaymentResult.Failure("INVALID_AMOUNT", "Amount must be positive")
        }
        
        val transactionId = "BT-${System.currentTimeMillis()}"
        println("✅ Bank Transfer of $$amount successful!")
        return PaymentResult.Success(transactionId, "Transfer completed to account $accountNumber")
    }
    
    override fun refund(transactionId: String, amount: Double): PaymentResult {
        println("🏦 Processing Bank Transfer refund...")
        println("✅ Refunded $$amount to account $accountNumber")
        return PaymentResult.Success(transactionId, "Refund transferred")
    }
    
    override fun getPaymentMethodName(): String = "Bank Transfer"
}

class CryptoPayment(private val walletAddress: String) : PaymentMethod {
    override fun pay(amount: Double): PaymentResult {
        println("₿ Processing Crypto Payment...")
        
        if (amount <= 0) {
            return PaymentResult.Failure("INVALID_AMOUNT", "Amount must be positive")
        }
        
        val transactionId = "CRYPTO-${System.currentTimeMillis()}"
        println("✅ Crypto payment of $$amount successful!")
        println("   Wallet: ${walletAddress.take(10)}...${walletAddress.takeLast(10)}")
        return PaymentResult.Success(transactionId, "Crypto transaction confirmed")
    }
    
    override fun refund(transactionId: String, amount: Double): PaymentResult {
        println("₿ Processing Crypto refund...")
        println("✅ Refunded $$amount to wallet")
        return PaymentResult.Success(transactionId, "Crypto refund sent")
    }
    
    override fun getPaymentMethodName(): String = "Cryptocurrency"
}

class WalletPayment(
    private val walletId: String,
    private var balance: Double
) : PaymentMethod {
    override fun pay(amount: Double): PaymentResult {
        println("👛 Processing Wallet payment...")
        println("   Current balance: $$balance")
        
        if (amount <= 0) {
            return PaymentResult.Failure("INVALID_AMOUNT", "Amount must be positive")
        }
        
        if (balance < amount) {
            println("❌ Insufficient balance!")
            return PaymentResult.Failure("INSUFFICIENT_FUNDS", "Balance: $$balance, Required: $$amount")
        }
        
        balance -= amount
        val transactionId = "WALLET-${System.currentTimeMillis()}"
        println("✅ Wallet payment of $$amount successful!")
        println("   Remaining balance: $$balance")
        return PaymentResult.Success(transactionId, "Paid from wallet")
    }
    
    override fun refund(transactionId: String, amount: Double): PaymentResult {
        println("👛 Processing Wallet refund...")
        balance += amount
        println("✅ Refunded $$amount to wallet")
        println("   New balance: $$balance")
        return PaymentResult.Success(transactionId, "Refund added to wallet")
    }
    
    override fun getPaymentMethodName(): String = "Digital Wallet (Balance: $$balance)"
}

class CashOnDelivery : PaymentMethod {
    override fun pay(amount: Double): PaymentResult {
        println("💵 Marked as Cash on Delivery...")
        
        if (amount <= 0) {
            return PaymentResult.Failure("INVALID_AMOUNT", "Amount must be positive")
        }
        
        val transactionId = "COD-${System.currentTimeMillis()}"
        println("✅ Cash on Delivery for $$amount confirmed!")
        return PaymentResult.Success(transactionId, "Will be collected on delivery")
    }
    
    override fun refund(transactionId: String, amount: Double): PaymentResult {
        println("💵 Processing COD refund...")
        println("✅ Cash refund of $$amount will be provided")
        return PaymentResult.Success(transactionId, "Cash refund arranged")
    }
    
    override fun getPaymentMethodName(): String = "Cash on Delivery"
}

// ===== LSP VIOLATION EXAMPLE (Commented out) =====

/*
// BAD EXAMPLE - Violates LSP!
class InvalidPaymentMethod : PaymentMethod {
    override fun pay(amount: Double): PaymentResult {
        // This breaks the contract by throwing an exception instead of returning a result
        throw UnsupportedOperationException("This payment method is not supported!")
    }
    
    override fun refund(transactionId: String, amount: Double): PaymentResult {
        // This also violates the contract
        throw UnsupportedOperationException("Refunds not supported!")
    }
    
    override fun getPaymentMethodName(): String = "Invalid Method"
}

// LECTURE: The above class violates LSP because:
// 1. It throws exceptions instead of returning PaymentResult
// 2. Code expecting PaymentMethod would crash when using this implementation
// 3. The caller cannot safely substitute this for other PaymentMethod implementations
*/

// ===== CHECKOUT SERVICE (High-level module) =====

class CheckoutService {
    fun finalizePayment(method: PaymentMethod, amount: Double): Boolean {
        println("\n💳 Initiating payment with ${method.getPaymentMethodName()}")
        println("   Amount: $$amount")
        
        // LSP in action: Any PaymentMethod should work here without the caller needing to know the details
        val result = method.pay(amount)
        
        return when (result) {
            is PaymentResult.Success -> {
                println("✅ Payment successful! Transaction ID: ${result.transactionId}")
                println("   ${result.message}\n")
                true
            }
            is PaymentResult.Failure -> {
                println("❌ Payment failed! Error: ${result.errorCode}")
                println("   ${result.message}\n")
                false
            }
        }
    }
    
    fun processRefund(method: PaymentMethod, transactionId: String, amount: Double): Boolean {
        println("\n🔄 Initiating refund with ${method.getPaymentMethodName()}")
        println("   Amount: $$amount")
        println("   Transaction: $transactionId")
        
        val result = method.refund(transactionId, amount)
        
        return when (result) {
            is PaymentResult.Success -> {
                println("✅ Refund successful!")
                println("   ${result.message}\n")
                true
            }
            is PaymentResult.Failure -> {
                println("❌ Refund failed! Error: ${result.errorCode}")
                println("   ${result.message}\n")
                false
            }
        }
    }
}

// DEMO: LSP allows us to use any payment method interchangeably
fun demonstrateLSP() {
    println("=== LSP Demonstration ===\n")
    
    val checkout = CheckoutService()
    val amount = 50.0
    
    // All these payment methods can be used interchangeably
    val creditCard = CreditCardPayment("1234567890123456", "123")
    val bankTransfer = BankTransfer("ACC-123456")
    val crypto = CryptoPayment("0x742d35Cc6634C0532925a3b844Bc9e7595f0bEb")
    val wallet = WalletPayment("WALLET-001", 100.0)
    val cod = CashOnDelivery()
    
    // The checkout service works with any PaymentMethod implementation
    checkout.finalizePayment(creditCard, amount)
    checkout.finalizePayment(wallet, amount)
    checkout.finalizePayment(cod, amount)
    
    println("✨ All payment methods are substitutable!")
    println("✨ The CheckoutService doesn't need to know which specific payment method is being used!")
}
