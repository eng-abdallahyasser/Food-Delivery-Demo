package com.abdallahyasser.demoapp.ui.navigation

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Onboarding : Routes("onboarding")
    object Login : Routes("login")
    object SignUp : Routes("signup")
    object Home : Routes("home")
    object Search : Routes("search")
    object RestaurantDetails : Routes("restaurant/{restaurantId}") {
        fun createRoute(restaurantId: String) = "restaurant/$restaurantId"
    }
    object FoodItemDetails : Routes("food/{foodId}") {
        fun createRoute(foodId: String) = "food/$foodId"
    }
    object Cart : Routes("cart")
    object Checkout : Routes("checkout")
    object OrderTracking : Routes("order/{orderId}") {
        fun createRoute(orderId: String) = "order/$orderId"
    }
    object OrderHistory : Routes("order_history")
    object Profile : Routes("profile")
}
