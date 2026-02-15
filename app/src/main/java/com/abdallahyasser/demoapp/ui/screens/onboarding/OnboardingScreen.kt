package com.abdallahyasser.demoapp.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit
) {
    var currentPage by remember { mutableStateOf(0) }
    val pages = listOf(
        OnboardingPage("🍔", "Discover Restaurants", "Browse hundreds of restaurants near you"),
        OnboardingPage("🛒", "Easy Ordering", "Add items to cart and customize your order"),
        OnboardingPage("🚚", "Fast Delivery", "Track your order in real-time")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = pages[currentPage].emoji,
                    fontSize = 100.sp
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = pages[currentPage].title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = pages[currentPage].description,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }

            // Page indicators
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                pages.indices.forEach { index ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (index == currentPage) 12.dp else 8.dp)
                            .background(
                                color = if (index == currentPage)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(50)
                            )
                    )
                }
            }

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentPage > 0) {
                    TextButton(onClick = { currentPage-- }) {
                        Text("Back")
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                Button(
                    onClick = {
                        if (currentPage < pages.size - 1) {
                            currentPage++
                        } else {
                            onNavigateToLogin()
                        }
                    },
                    modifier = Modifier.widthIn(min = 120.dp)
                ) {
                    Text(if (currentPage < pages.size - 1) "Next" else "Get Started")
                }
            }
        }
    }
}

data class OnboardingPage(
    val emoji: String,
    val title: String,
    val description: String
)
