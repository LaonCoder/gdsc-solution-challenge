package com.gdsc.wildlives

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gdsc.wildlives.navigation.Navigation
import com.gdsc.wildlives.pages.login.LoginViewModel
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.ui.theme.MainUiTheme
import com.gdsc.wildlives.ui.theme.colorPrimary
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel: LoginViewModel = viewModel()
            val searchViewModel: SearchViewModel = viewModel()

            MainUiTheme {
                MainApp(
                    loginViewModel = loginViewModel,
                    searchViewModel = searchViewModel
                )
            }
        }
    }

    @Composable
    fun MainApp(
        loginViewModel: LoginViewModel,
        searchViewModel: SearchViewModel
    ) {
        MainUiTheme {
            Surface(color = colorPrimary) {
                Navigation(
                    loginViewModel = loginViewModel,
                    searchViewModel = searchViewModel
                )
            }
        }
    }
}

