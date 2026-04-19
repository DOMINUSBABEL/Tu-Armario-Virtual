package com.myapplication.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.gamification.GameState

@Composable
fun LoginScreen(onNavigateToTutorial: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF1A1A2E), Color(0xFF252542))
            )
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "DressYourself",
                style = TextStyle(
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    shadow = Shadow(color = Color(0xFFFF1493), blurRadius = 20f)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Studio",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00FFFF),
                    letterSpacing = 4.sp,
                    shadow = Shadow(color = Color(0xFF00FFFF), blurRadius = 10f)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Log in to earn 10 Style Points! \uD83D\uDC8E",
                style = MaterialTheme.typography.subtitle1,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.White.copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFFF1493),
                    unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                    textColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White.copy(alpha = 0.6f)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFFF1493),
                    unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                    textColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    GameState.addAction("Login Welcome Bonus", 10)
                    onNavigateToTutorial()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF1493))
            ) {
                Text("ENTER THE STUDIO", color = Color.White, style = MaterialTheme.typography.button)
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    GameState.addAction("Login Welcome Bonus", 10)
                    onNavigateToTutorial()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent, contentColor = Color(0xFF00FFFF))
            ) {
                Text("CONTINUE WITH GOOGLE", style = MaterialTheme.typography.button)
            }
        }
    }
}
