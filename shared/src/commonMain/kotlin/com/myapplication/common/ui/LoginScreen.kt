package com.myapplication.common.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.myapplication.common.gamification.GameState

@Composable
fun LoginScreen(onNavigateToTutorial: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "AI Outfit Recommender", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Welcome back! Login to earn 10 Style Points.",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                GameState.addAction("Login Welcome Bonus", 10)
                onNavigateToTutorial()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                GameState.addAction("Login Welcome Bonus", 10)
                onNavigateToTutorial()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login with Google")
        }
    }
}
