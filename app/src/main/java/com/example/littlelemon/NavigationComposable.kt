package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import android.content.Context


@Composable
fun NavigationComposable(navController: NavHostController, context: Context) {
    NavHost(
        navController = navController,
        startDestination =determineStartDestination(context)
            )
        {
        composable(Home.route) {
            Home(navController)
        }
        composable(Profile.route) {
            Profile(navController)
        }
            composable(Onboarding.route){
                onboarding(navController)
            }
    }
}
private fun determineStartDestination(context : Context): String {
    val sharedPrefences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
    if(sharedPrefences.getBoolean("userRegistered", false)) {
        return Home.route
    } else {
        return Onboarding.route
    }
}
