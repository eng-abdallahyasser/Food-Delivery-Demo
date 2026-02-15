package com.abdallahyasser.demoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abdallahyasser.demoapp.ui.screens.auth.LoginScreen
import com.abdallahyasser.demoapp.ui.screens.auth.SignUpScreen
import com.abdallahyasser.demoapp.ui.screens.cart.CartScreen
import com.abdallahyasser.demoapp.ui.screens.checkout.CheckoutScreen
import com.abdallahyasser.demoapp.ui.screens.food.FoodItemDetailsScreen
import com.abdallahyasser.demoapp.ui.screens.home.HomeScreen
import com.abdallahyasser.demoapp.ui.screens.onboarding.OnboardingScreen
import com.abdallahyasser.demoapp.ui.screens.order.OrderHistoryScreen
import com.abdallahyasser.demoapp.ui.screens.order.OrderTrackingScreen
import com.abdallahyasser.demoapp.ui.screens.profile.ProfileScreen
import com.abdallahyasser.demoapp.ui.screens.restaurant.RestaurantDetailsScreen
import com.abdallahyasser.demoapp.ui.screens.search.SearchScreen
import com.abdallahyasser.demoapp.ui.screens.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {
        // Splash
        composable(Routes.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Routes.Onboarding.route) {
                        popUpTo(Routes.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // Onboarding
        composable(Routes.Onboarding.route) {
            OnboardingScreen(
                onNavigateToLogin = {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // Login
        composable(Routes.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Routes.SignUp.route)
                }
            )
        }

        // Sign Up
        composable(Routes.SignUp.route) {
            SignUpScreen(
                onNavigateToHome = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.SignUp.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // Home
        composable(Routes.Home.route) {
            HomeScreen(
                onNavigateToRestaurant = { restaurantId ->
                    navController.navigate(Routes.RestaurantDetails.createRoute(restaurantId))
                },
                onNavigateToCart = {
                    navController.navigate(Routes.Cart.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Routes.Profile.route)
                },
                onNavigateToSearch = {
                    navController.navigate(Routes.Search.route)
                }
            )
        }

        // Search
        composable(Routes.Search.route) {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToFoodItem = { foodId ->
                    navController.navigate(Routes.FoodItemDetails.createRoute(foodId))
                }
            )
        }

        // Restaurant Details
        composable(
            route = Routes.RestaurantDetails.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.StringType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId") ?: ""
            RestaurantDetailsScreen(
                restaurantId = restaurantId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToFoodItem = { foodId ->
                    navController.navigate(Routes.FoodItemDetails.createRoute(foodId))
                }
            )
        }

        // Food Item Details
        composable(
            route = Routes.FoodItemDetails.route,
            arguments = listOf(navArgument("foodId") { type = NavType.StringType })
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getString("foodId") ?: ""
            FoodItemDetailsScreen(
                foodId = foodId,
                onNavigateBack = { navController.popBackStack() },
                onAddToCart = {
                    navController.navigate(Routes.Cart.route)
                }
            )
        }

        // Cart
        composable(Routes.Cart.route) {
            CartScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCheckout = {
                    navController.navigate(Routes.Checkout.route)
                }
            )
        }

        // Checkout
        composable(Routes.Checkout.route) {
            CheckoutScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToOrderTracking = { orderId ->
                    navController.navigate(Routes.OrderTracking.createRoute(orderId)) {
                        popUpTo(Routes.Home.route)
                    }
                }
            )
        }

        // Order Tracking
        composable(
            route = Routes.OrderTracking.route,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            OrderTrackingScreen(
                orderId = orderId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Home.route) { inclusive = true }
                    }
                }
            )
        }

        // Order History
        composable(Routes.OrderHistory.route) {
            OrderHistoryScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToOrderTracking = { orderId ->
                    navController.navigate(Routes.OrderTracking.createRoute(orderId))
                }
            )
        }

        // Profile
        composable(Routes.Profile.route) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToOrderHistory = {
                    navController.navigate(Routes.OrderHistory.route)
                }
            )
        }
    }
}
