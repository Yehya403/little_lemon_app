package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.PrimaryGreen
import com.example.littlelemon.ui.theme.PrimaryYellow

@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString("firstName", "") ?: "Not Found"
    val lastName = sharedPreferences.getString("lastName", "") ?: "Not Found"
    val email = sharedPreferences.getString("email", "") ?: "Not Found"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "app_logo",
            modifier = Modifier
                .fillMaxWidth()
                .size(100.dp)
        )

        Text(
            text = "Personal Information",
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "First Name")
            OutlinedTextField(
                value = firstName,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Last Name")
            OutlinedTextField(
                value = lastName,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Email Address")
            OutlinedTextField(
                value = email,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
        Button(onClick = {
            sharedPreferences.edit().clear().apply()
            navController.navigate(Onboarding.route)
        },
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(2.dp, PrimaryGreen),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = PrimaryYellow,
                contentColor = Color.Black
            )
        ) {
            Text(text = "Log out", fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    Profile(navController)
}