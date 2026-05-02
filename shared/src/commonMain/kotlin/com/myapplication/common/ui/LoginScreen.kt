package com.myapplication.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.myapplication.common.gamification.GameState
import com.myapplication.common.ui.components.GlassPanel
import com.myapplication.common.ui.components.NeonMirrorLogo
import com.myapplication.common.ui.components.bouncingClickable
import com.myapplication.common.ui.theme.DeepPurple
import com.myapplication.common.ui.theme.ElectricCyan
import com.myapplication.common.ui.theme.OnyxBlack

@Composable
fun LoginScreen(onNavigateToTutorial: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(OnyxBlack, DeepPurple.copy(alpha = 0.2f), OnyxBlack)
    )

    Box(
        modifier = Modifier.fillMaxSize().background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NeonMirrorLogo(modifier = Modifier.padding(bottom = 24.dp), size = 120.dp)

            Text(
                text = "DressYourself",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Welcome back! Login to earn 10 Style Points.",
                style = MaterialTheme.typography.subtitle1,
                color = ElectricCyan
            )
            Spacer(modifier = Modifier.height(48.dp))

            GlassPanel(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                Column(modifier = Modifier.padding(24.dp)) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = MaterialTheme.colors.onBackground.copy(alpha=0.7f)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ElectricCyan,
                            unfocusedBorderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
                            textColor = MaterialTheme.colors.onBackground
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", color = MaterialTheme.colors.onBackground.copy(alpha=0.7f)) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ElectricCyan,
                            unfocusedBorderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
                            textColor = MaterialTheme.colors.onBackground
                        )
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            GameState.addAction("Login Welcome Bonus", 10)
                            onNavigateToTutorial()
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp).bouncingClickable {
                            GameState.addAction("Login Welcome Bonus", 10)
                            onNavigateToTutorial()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    ) {
                        Text("LOGIN")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = {
                            GameState.addAction("Login Welcome Bonus", 10)
                            onNavigateToTutorial()
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp).bouncingClickable {
                            GameState.addAction("Login Welcome Bonus", 10)
                            onNavigateToTutorial()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = androidx.compose.ui.graphics.Color.Transparent)
                    ) {
                        Text("LOGIN WITH GOOGLE", color = MaterialTheme.colors.onBackground)
                    }
                }
            }
        }
    }
}
