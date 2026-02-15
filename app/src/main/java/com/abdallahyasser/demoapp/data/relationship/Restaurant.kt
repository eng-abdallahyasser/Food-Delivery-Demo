package com.abdallahyasser.demoapp.data.relationship

// DEMO: Aggregation - Restaurant HAS-A Menu. 
// A Menu can exist independently (e.g., shared across branches or stored in a DB),
// but the Restaurant uses it.
class Restaurant(
    val id: String,
    val name: String,
    var menu: Menu? = null
) {
    fun updateMenu(newMenu: Menu) {
        this.menu = newMenu
    }
}
