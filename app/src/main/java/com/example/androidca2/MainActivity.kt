package com.example.androidca2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidca2.ui.theme.AndroidCA2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidCA2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Fixed: Changed 130dp to 16dp. 130dp was making the UI too small.
                    Box(modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                        .fillMaxSize()) {
                        LoginNav()
                    }
                }
            }
        }
    }
}

@Composable
fun LoginNav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(navController)
        }
        composable("dashboard/{message}") { backStack ->
            val message = backStack.arguments?.getString("message") ?: ""
            Dashboard(navController, message)
        }
    }
}

@Composable
fun Login(navController: NavController) {
    // verticalScroll allows the UI to work on smaller screens/landscape
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isChecked by remember { mutableStateOf(false) }

        Text(
            text = "Login",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Balanced size for the icon
        Image(
            painter = painterResource(id = R.drawable.loginicon),
            contentDescription = "Login Icon",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(), // Hides password dots
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = isChecked, onCheckedChange = { isChecked = it })
            Text(text = "Remember me", fontSize = 14.sp)
        }

        val context = LocalContext.current
        Button(
            onClick = {
                if (email == "User@gmail.com" && password == "User123") {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    // Fixed logic: Navigation should happen if login is correct
                    navController.navigate("dashboard/$email")
                } else {
                    Toast.makeText(context, "Invalid Login Details", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .height(50.dp) // Proper button height
        ) {
            Text(text = "Login", fontSize = 18.sp)
        }
    }
}

@Composable
fun Dashboard(navController: NavController, message: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome", fontSize = 20.sp)
        Text(text = "$message", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(text = "Logout")
        }
    }
}
