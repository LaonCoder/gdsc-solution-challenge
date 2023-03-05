package com.gdsc.wildlives.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.pages.CheckoutScreen
import com.gdsc.wildlives.pages.FlowerDetailsScreen
import com.gdsc.wildlives.pages.PopularListScreen
import com.gdsc.wildlives.pages.components.Dashboard
import com.gdsc.wildlives.pages.detail.DetailViewModel
import com.gdsc.wildlives.pages.detail.screen.DetailScreen
import com.gdsc.wildlives.pages.login.LoginScreen
import com.gdsc.wildlives.pages.login.LoginViewModel
import com.gdsc.wildlives.pages.login.SignUpScreen
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.pages.splash.SplashScreen
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URLDecoder

@Composable
fun Navigation(
    loginViewModel: LoginViewModel,
    searchViewModel: SearchViewModel,
    detailViewModel: DetailViewModel,
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

        composable(Screen.DetailsScreen.route) {

        }

        composable(
            route = Screen.DetailScreen.route + "?animal={animal}",
            arguments = listOf(navArgument("animal") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { backStackEntry ->
            val animalDataJson = backStackEntry.arguments?.getString("animal")
            Log.d("animalDataJson", animalDataJson ?: "")
            val passedAnimalData = Json.decodeFromString<AnimalData>(animalDataJson ?: "")
            DetailScreen(
                animalData = passedAnimalData,
                navController = navController,
                detailViewModel = detailViewModel,
            )
        }


        composable(Screen.PopularListScreen.route) {
            PopularListScreen(navController = navController)
        }

        composable(Screen.AddToCartScreen.route) {
            CheckoutScreen()
        }

    }
}