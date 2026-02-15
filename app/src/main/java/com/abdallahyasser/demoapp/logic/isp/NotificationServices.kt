package com.abdallahyasser.demoapp.logic.isp

// LECTURE: ISP - Clients should not be forced to depend on methods they do not use.

// ===== VIOLATION EXAMPLE =====

// VIOLATION: A monolithic interface
interface MultiService {
    fun sendEmail()
    fun sendSMS()
    fun sendPushNotification()
    fun sendWhatsApp()
    fun sendInAppNotification()
}

// If a delivery driver only needs SMS, they are still forced to implement Email, Push, WhatsApp, and InApp methods.
// This violates ISP because the class depends on methods it doesn't use.

// ===== ISP SOLUTION - Split into specific interfaces =====

interface EmailService {
    fun sendEmail(address: String, subject: String, message: String)
    fun sendBulkEmail(addresses: List<String>, subject: String, message: String)
}

interface SMSService {
    fun sendSMS(phoneNumber: String, message: String)
    fun sendBulkSMS(phoneNumbers: List<String>, message: String)
}

interface PushService {
    fun sendPush(userId: String, title: String, message: String)
    fun sendPushToAll(title: String, message: String)
}

interface WhatsAppService {
    fun sendWhatsApp(phoneNumber: String, message: String)
    fun sendWhatsAppWithMedia(phoneNumber: String, message: String, mediaUrl: String)
}

interface InAppNotificationService {
    fun sendInAppNotification(userId: String, message: String, priority: String)
    fun markAsRead(userId: String, notificationId: String)
}

// ===== IMPLEMENTATIONS =====

// IMPLEMENTATION: A class can implement one or many interfaces as needed.

class CustomerNotifier : EmailService, PushService {
    override fun sendEmail(address: String, subject: String, message: String) {
        println("📧 Email sent to $address")
        println("   Subject: $subject")
        println("   Message: $message")
    }
    
    override fun sendBulkEmail(addresses: List<String>, subject: String, message: String) {
        println("📧 Bulk email sent to ${addresses.size} recipients")
        println("   Subject: $subject")
    }

    override fun sendPush(userId: String, title: String, message: String) {
        println("📱 Push notification sent to user $userId")
        println("   Title: $title")
        println("   Message: $message")
    }
    
    override fun sendPushToAll(title: String, message: String) {
        println("📱 Push notification sent to ALL users")
        println("   Title: $title")
    }
}

class DriverNotifier : SMSService, WhatsAppService {
    override fun sendSMS(phoneNumber: String, message: String) {
        println("📲 SMS sent to driver at $phoneNumber")
        println("   Message: $message")
    }
    
    override fun sendBulkSMS(phoneNumbers: List<String>, message: String) {
        println("📲 Bulk SMS sent to ${phoneNumbers.size} drivers")
    }
    
    override fun sendWhatsApp(phoneNumber: String, message: String) {
        println("💬 WhatsApp message sent to $phoneNumber")
        println("   Message: $message")
    }
    
    override fun sendWhatsAppWithMedia(phoneNumber: String, message: String, mediaUrl: String) {
        println("💬 WhatsApp message with media sent to $phoneNumber")
        println("   Message: $message")
        println("   Media: $mediaUrl")
    }
}

class AdminNotifier : EmailService, SMSService, PushService, WhatsAppService, InAppNotificationService {
    // Admins need ALL notification types
    
    override fun sendEmail(address: String, subject: String, message: String) {
        println("📧 [ADMIN] Email sent to $address - $subject")
    }
    
    override fun sendBulkEmail(addresses: List<String>, subject: String, message: String) {
        println("📧 [ADMIN] Bulk email to ${addresses.size} addresses")
    }
    
    override fun sendSMS(phoneNumber: String, message: String) {
        println("📲 [ADMIN] SMS sent to $phoneNumber")
    }
    
    override fun sendBulkSMS(phoneNumbers: List<String>, message: String) {
        println("📲 [ADMIN] Bulk SMS to ${phoneNumbers.size} numbers")
    }
    
    override fun sendPush(userId: String, title: String, message: String) {
        println("📱 [ADMIN] Push to user $userId - $title")
    }
    
    override fun sendPushToAll(title: String, message: String) {
        println("📱 [ADMIN] Push to ALL users - $title")
    }
    
    override fun sendWhatsApp(phoneNumber: String, message: String) {
        println("💬 [ADMIN] WhatsApp to $phoneNumber")
    }
    
    override fun sendWhatsAppWithMedia(phoneNumber: String, message: String, mediaUrl: String) {
        println("💬 [ADMIN] WhatsApp with media to $phoneNumber")
    }
    
    override fun sendInAppNotification(userId: String, message: String, priority: String) {
        println("🔔 [ADMIN] In-app notification to user $userId")
        println("   Priority: $priority")
        println("   Message: $message")
    }
    
    override fun markAsRead(userId: String, notificationId: String) {
        println("✅ [ADMIN] Marked notification $notificationId as read for user $userId")
    }
}

class BasicUserNotifier : EmailService, PushService, InAppNotificationService {
    // Basic users only need email, push, and in-app notifications
    
    override fun sendEmail(address: String, subject: String, message: String) {
        println("📧 [USER] Email to $address")
        println("   Subject: $subject")
    }
    
    override fun sendBulkEmail(addresses: List<String>, subject: String, message: String) {
        println("📧 [USER] Bulk email to ${addresses.size} users")
    }
    
    override fun sendPush(userId: String, title: String, message: String) {
        println("📱 [USER] Push notification to $userId")
        println("   Title: $title")
    }
    
    override fun sendPushToAll(title: String, message: String) {
        println("📱 [USER] Broadcast push notification")
    }
    
    override fun sendInAppNotification(userId: String, message: String, priority: String) {
        println("🔔 [USER] In-app notification")
        println("   Priority: $priority")
    }
    
    override fun markAsRead(userId: String, notificationId: String) {
        println("✅ [USER] Notification marked as read")
    }
}

class MarketingNotifier : EmailService, SMSService, PushService, WhatsAppService {
    // Marketing team needs email, SMS, push, and WhatsApp but NOT in-app
    
    override fun sendEmail(address: String, subject: String, message: String) {
        println("📧 [MARKETING] Promotional email to $address")
        println("   Subject: $subject")
    }
    
    override fun sendBulkEmail(addresses: List<String>, subject: String, message: String) {
        println("📧 [MARKETING] Campaign email to ${addresses.size} subscribers")
        println("   Subject: $subject")
    }
    
    override fun sendSMS(phoneNumber: String, message: String) {
        println("📲 [MARKETING] Promotional SMS to $phoneNumber")
    }
    
    override fun sendBulkSMS(phoneNumbers: List<String>, message: String) {
        println("📲 [MARKETING] Campaign SMS to ${phoneNumbers.size} numbers")
    }
    
    override fun sendPush(userId: String, title: String, message: String) {
        println("📱 [MARKETING] Promotional push to $userId")
    }
    
    override fun sendPushToAll(title: String, message: String) {
        println("📱 [MARKETING] Campaign push to all users")
        println("   Title: $title")
    }
    
    override fun sendWhatsApp(phoneNumber: String, message: String) {
        println("💬 [MARKETING] Promotional WhatsApp to $phoneNumber")
    }
    
    override fun sendWhatsAppWithMedia(phoneNumber: String, message: String, mediaUrl: String) {
        println("💬 [MARKETING] WhatsApp campaign with media")
        println("   Media: $mediaUrl")
    }
}

// ===== NOTIFICATION MANAGER =====

class NotificationManager {
    fun notifyOrderPlaced(emailService: EmailService, userId: String, orderId: String) {
        emailService.sendEmail(
            "user@example.com",
            "Order Placed",
            "Your order $orderId has been placed successfully!"
        )
    }
    
    fun notifyDriver(smsService: SMSService, phoneNumber: String, orderId: String) {
        smsService.sendSMS(
            phoneNumber,
            "New delivery: Order $orderId is ready for pickup!"
        )
    }
    
    fun sendPromotion(
        emailService: EmailService,
        pushService: PushService,
        addresses: List<String>
    ) {
        emailService.sendBulkEmail(addresses, "Special Offer!", "Get 20% off your next order!")
        pushService.sendPushToAll("Limited Time Offer", "20% off - Order now!")
    }
}

// DEMO: ISP in action
fun demonstrateISP() {
    println("=== ISP Demonstration ===\n")
    
    val customerNotifier = CustomerNotifier()
    val driverNotifier = DriverNotifier()
    val adminNotifier = AdminNotifier()
    val basicUserNotifier = BasicUserNotifier()
    val marketingNotifier = MarketingNotifier()
    
    val manager = NotificationManager()
    
    // Each notifier only implements the interfaces it needs
    manager.notifyOrderPlaced(customerNotifier, "user-123", "ORD-456")
    manager.notifyDriver(driverNotifier, "+1234567890", "ORD-456")
    
    println("\n✨ Each class only implements the notification methods it actually uses!")
    println("✨ No class is forced to implement methods it doesn't need!")
}
