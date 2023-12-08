package com.example.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.PrimaryGreen
import com.example.littlelemon.ui.theme.PrimaryYellow

@Composable
fun onboarding(navController: NavHostController) {
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize().padding(20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "app_logo",
            modifier = Modifier
                .fillMaxWidth()
                .size(100.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = PrimaryGreen)
                .height(100.dp)
                ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Let's get to know you",
                style = MaterialTheme.typography.h6,
                    color = Color.White
            )
        }

        Text(
            text = "Personal Information",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(text = "First name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp)
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = "Second name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email Address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 28.dp)
        )
        Button(
            onClick = { if(firstName.isBlank() || lastName.isBlank() || email.isBlank()){
            showToast(context,"Registration unsuccessful. Please enter all data.")
            } else {
                showToast(context, "Registration successful!")
                saveUserData(context,firstName,lastName,email)
                navController.navigate(Home.route)
            }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryYellow, contentColor = Color.Black)
        , border = BorderStroke(2.dp, PrimaryGreen)
        ) {
            Text(text = "Register", style = TextStyle(fontWeight = FontWeight.Bold))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun onboadringPreview() {
    val navController = rememberNavController()
    onboarding(navController)
}

private fun showToast(context : Context, message : String){
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()
}

private fun saveUserData(context : Context,firstName :String , lastName : String , email : String){
    val sharedPref = context.getSharedPreferences("UserPrefs",Context.MODE_PRIVATE)
sharedPref.edit {
    putString("firstName",firstName)
    putString("lastName",lastName)
    putString("email",email)
    putBoolean("userRegistered", true)

}
}