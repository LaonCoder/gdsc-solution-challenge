package com.gdsc.wildlives.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gdsc.wildlives.pages.CheckoutScreen
import com.gdsc.wildlives.pages.FlowerDetailsScreen
import com.gdsc.wildlives.pages.PopularListScreen
import com.gdsc.wildlives.pages.components.Dashboard
import com.gdsc.wildlives.pages.login.LoginScreen
import com.gdsc.wildlives.pages.login.LoginViewModel
import com.gdsc.wildlives.pages.login.SignUpScreen
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.pages.splash.SplashScreen

@Composable
fun Navigation(
    loginViewModel: LoginViewModel,
    searchViewModel: SearchViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }

        composable(Screen.LoginScreen.route) {
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }

        composable(Screen.HomeScreen.route) {
            Dashboard(
                navController = navController,
                searchViewModel = searchViewModel
            )
        }

        composable(Screen.PopularListScreen.route) {
            PopularListScreen(navController = navController)
        }
        composable(Screen.DetailsScreen.route) {
            FlowerDetailsScreen(navController = navController)
        }
        composable(Screen.AddToCartScreen.route) {
            CheckoutScreen()
        }

    }
}