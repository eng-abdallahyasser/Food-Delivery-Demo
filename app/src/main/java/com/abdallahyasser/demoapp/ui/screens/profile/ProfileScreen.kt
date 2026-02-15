package com.abdallahyasser.demoapp.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToOrderHistory: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "👤",
                    fontSize = 80.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "John Doe",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "john@example.com",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }

            Divider()

            // Profile Options
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                ProfileMenuItem(
                    icon = Icons.Default.ShoppingCart,
                    title = "Order History",
                    onClick = onNavigateToOrderHistory
                )
                ProfileMenuItem(
                    icon = Icons.Default.LocationOn,
                    title = "Saved Addresses",
                    onClick = { /* TODO */ }
                )
                ProfileMenuItem(
                    icon = Icons.Default.CreditCard,
                    title = "Payment Methods",
                    onClick = { /* TODO */ }
                )
                ProfileMenuItem(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    onClick = { /* TODO */ }
                )
                ProfileMenuItem(
                    icon = Icons.Default.Settings,
                    title = "Settings",
                    onClick = { /* TODO */ }
                )
                ProfileMenuItem(
                    icon = Icons.Default.Help,
                    title = "Help & Support",
                    onClick = { /* TODO */ }
                )
                ProfileMenuItem(
                    icon = Icons.Default.Logout,
                    title = "Logout",
                    onClick = { /* TODO */ },
                    isDestructive = true
                )
            }
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isDestructive)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = title,
                fontSize = 16.sp,
                color = if (isDestructive)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        }
    }
}
