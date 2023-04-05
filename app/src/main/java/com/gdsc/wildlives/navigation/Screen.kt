package com.gdsc.wildlives.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object SignUpScreen : Screen("sign_up_screen")
    object HomeScreen : Screen("home_screen")
    object SearchScreen : Screen("search_screen")
    object DetailScreen : Screen("detail_screen")
    object ClassificationScreen : Screen("classification_screen")
    object PopularListScreen : Screen("popular_list_screen")
    object DetailsScreen : Screen("details_screen")
    object AddToCartScreen : Screen("add_to_cart_screen")
}
