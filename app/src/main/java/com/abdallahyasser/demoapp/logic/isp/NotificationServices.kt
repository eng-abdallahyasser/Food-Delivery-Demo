package com.abdallahyasser.demoapp.logic.isp

// LECTURE: ISP - Clients should not be forced to depend on methods they do not use.

// VIOLATION: A monolithic interface
interface MultiService {
    fun sendEmail()
    fun sendSMS()
    fun sendPushNotification()
}

// If a delivery driver only needs SMS, they are still forced to see Email and Push methods.

// DEMO: ISP Solution - Split into specific interfaces

interface EmailService {
    fun sendEmail(address: String, message: String)
}

interface SMSService {
    fun sendSMS(phoneNumber: String, message: String)
}

interface PushService {
    fun sendPush(userId: String, message: String)
}

// IMPLEMENTATION: A class can implement one or many as needed.
class CustomerNotifier : EmailService, PushService {
    override fun sendEmail(address: String, message: String) {
        println("Email sent to $address")
    }

    override fun sendPush(userId: String, message: String) {
        println("Push sent to user $userId")
    }
}

class DriverNotifier : SMSService {
    override fun sendSMS(phoneNumber: String, message: String) {
        println("SMS sent to driver at $phoneNumber")
    }
}
