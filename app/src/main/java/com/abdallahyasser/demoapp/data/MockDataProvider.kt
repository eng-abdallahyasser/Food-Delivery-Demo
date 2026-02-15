package com.abdallahyasser.demoapp.data

import com.abdallahyasser.demoapp.data.model.FoodItem
import com.abdallahyasser.demoapp.data.model.User
import com.abdallahyasser.demoapp.data.relationship.Menu
import com.abdallahyasser.demoapp.data.relationship.Order
import com.abdallahyasser.demoapp.data.relationship.OrderItem
import com.abdallahyasser.demoapp.data.relationship.Restaurant

// DEMO: A static data source to provide initial data for the application.
object MockDataProvider {

    // ===== FOOD ITEMS =====
    val foodItems = listOf(
        // Burgers
        FoodItem("f1", "Classic Burger", "Juicy beef patty with lettuce and tomato", 10.99),
        FoodItem("f2", "Double Cheeseburger", "Two beef patties with double cheese", 13.99),
        FoodItem("f3", "Bacon Burger", "Beef patty with crispy bacon and BBQ sauce", 12.49),
        FoodItem("f4", "Veggie Burger", "Plant-based patty with avocado", 11.99),
        
        // Sides
        FoodItem("f5", "French Fries", "Crispy salted fries", 3.99),
        FoodItem("f6", "Onion Rings", "Golden fried onion rings", 4.49),
        FoodItem("f7", "Mozzarella Sticks", "Breaded mozzarella with marinara", 5.99),
        
        // Pizza
        FoodItem("f8", "Margherita Pizza", "Fresh mozzarella and basil", 12.99),
        FoodItem("f9", "Pepperoni Pizza", "Classic pepperoni and cheese", 14.99),
        FoodItem("f10", "BBQ Chicken Pizza", "Grilled chicken with BBQ sauce", 15.99),
        FoodItem("f11", "Veggie Supreme Pizza", "Loaded with fresh vegetables", 13.99),
        
        // Salads
        FoodItem("f12", "Caesar Salad", "Fresh romaine with caesar dressing", 8.99),
        FoodItem("f13", "Greek Salad", "Feta, olives, and fresh vegetables", 9.49),
        FoodItem("f14", "Cobb Salad", "Chicken, bacon, egg, and avocado", 11.99),
        
        // Drinks
        FoodItem("f15", "Coca Cola", "Classic soft drink", 2.49),
        FoodItem("f16", "Fresh Orange Juice", "Freshly squeezed orange juice", 3.99),
        FoodItem("f17", "Iced Coffee", "Cold brew coffee with ice", 4.49),
        
        // Desserts
        FoodItem("f18", "Chocolate Cake", "Rich chocolate layer cake", 6.99),
        FoodItem("f19", "Ice Cream Sundae", "Vanilla ice cream with toppings", 5.49),
        FoodItem("f20", "Apple Pie", "Classic apple pie with cinnamon", 5.99)
    )

    // ===== MENUS =====
    val menus = listOf(
        Menu("m1", "Burger Joint Menu", foodItems.subList(0, 7)), // Burgers + Sides
        Menu("m2", "Pizza Palace Menu", foodItems.subList(7, 11)), // Pizzas
        Menu("m3", "Healthy Hub Menu", foodItems.subList(11, 14)), // Salads
        Menu("m4", "Full Menu", foodItems) // All items
    )

    // ===== RESTAURANTS =====
    val restaurants = listOf(
        Restaurant("r1", "Big Burger Joint", menus[0]),
        Restaurant("r2", "Amazing Pizza Palace", menus[1]),
        Restaurant("r3", "The Healthy Hub", menus[2]),
        Restaurant("r4", "Food Paradise", menus[3])
    )

    // ===== CUSTOMERS (USERS) =====
    val customers = listOf(
        User("c1", "John Doe", "john.doe@email.com", "+1234567890"),
        User("c2", "Jane Smith", "jane.smith@email.com", "+1234567891"),
        User("c3", "Mike Johnson", "mike.j@email.com", "+1234567892"),
        User("c4", "Sarah Williams", "sarah.w@email.com", "+1234567893"),
        User("c5", "David Brown", "david.b@email.com", "+1234567894")
    )

    // ===== DRIVERS =====
    val drivers = listOf(
        User("d1", "Alex Driver", "alex.driver@delivery.com", "+1987654321"),
        User("d2", "Maria Rodriguez", "maria.r@delivery.com", "+1987654322"),
        User("d3", "Tom Wilson", "tom.w@delivery.com", "+1987654323")
    )

    // ===== ORDERS =====
    val orders: List<Order> by lazy {
        listOf(
            // Order 1: John's burger order
            Order("ord1", customers[0]).apply {
                addItem(OrderItem(foodItems[0], 2)) // 2x Classic Burger
                addItem(OrderItem(foodItems[4], 1)) // 1x French Fries
                addItem(OrderItem(foodItems[14], 2)) // 2x Coca Cola
                driver = drivers[0]
            },
            
            // Order 2: Jane's pizza order
            Order("ord2", customers[1]).apply {
                addItem(OrderItem(foodItems[8], 1)) // 1x Pepperoni Pizza
                addItem(OrderItem(foodItems[15], 2)) // 2x Orange Juice
                addItem(OrderItem(foodItems[17], 1)) // 1x Chocolate Cake
                driver = drivers[1]
            },
            
            // Order 3: Mike's healthy order
            Order("ord3", customers[2]).apply {
                addItem(OrderItem(foodItems[11], 1)) // 1x Caesar Salad
                addItem(OrderItem(foodItems[13], 1)) // 1x Cobb Salad
                addItem(OrderItem(foodItems[15], 1)) // 1x Orange Juice
                driver = drivers[2]
            },
            
            // Order 4: Sarah's large order
            Order("ord4", customers[3]).apply {
                addItem(OrderItem(foodItems[1], 3)) // 3x Double Cheeseburger
                addItem(OrderItem(foodItems[4], 3)) // 3x French Fries
                addItem(OrderItem(foodItems[5], 2)) // 2x Onion Rings
                addItem(OrderItem(foodItems[14], 3)) // 3x Coca Cola
                addItem(OrderItem(foodItems[17], 2)) // 2x Chocolate Cake
                driver = drivers[0]
            },
            
            // Order 5: David's pizza party
            Order("ord5", customers[4]).apply {
                addItem(OrderItem(foodItems[8], 2)) // 2x Pepperoni Pizza
                addItem(OrderItem(foodItems[9], 1)) // 1x BBQ Chicken Pizza
                addItem(OrderItem(foodItems[10], 1)) // 1x Veggie Supreme Pizza
                addItem(OrderItem(foodItems[14], 4)) // 4x Coca Cola
                driver = drivers[1]
            },
            
            // Order 6: John's second order (no driver yet)
            Order("ord6", customers[0]).apply {
                addItem(OrderItem(foodItems[3], 1)) // 1x Veggie Burger
                addItem(OrderItem(foodItems[11], 1)) // 1x Caesar Salad
                addItem(OrderItem(foodItems[16], 1)) // 1x Iced Coffee
            },
            
            // Order 7: Jane's breakfast order
            Order("ord7", customers[1]).apply {
                addItem(OrderItem(foodItems[16], 2)) // 2x Iced Coffee
                addItem(OrderItem(foodItems[19], 1)) // 1x Apple Pie
            },
            
            // Order 8: Mike's quick snack
            Order("ord8", customers[2]).apply {
                addItem(OrderItem(foodItems[6], 1)) // 1x Mozzarella Sticks
                addItem(OrderItem(foodItems[14], 1)) // 1x Coca Cola
                driver = drivers[2]
            }
        )
    }

    // ===== HELPER FUNCTIONS =====
    
    fun getRestaurantById(id: String): Restaurant? {
        return restaurants.find { it.id == id }
    }

    fun getAllFoodItems(): List<FoodItem> = foodItems
    
    fun getCustomerById(id: String): User? {
        return customers.find { it.id == id }
    }
    
    fun getDriverById(id: String): User? {
        return drivers.find { it.id == id }
    }
    
    fun getOrderById(id: String): Order? {
        return orders.find { it.id == id }
    }
    
    fun getOrdersByCustomerId(customerId: String): List<Order> {
        return orders.filter { it.customer.id == customerId }
    }
    
    fun getAllOrders(): List<Order> = orders
}
