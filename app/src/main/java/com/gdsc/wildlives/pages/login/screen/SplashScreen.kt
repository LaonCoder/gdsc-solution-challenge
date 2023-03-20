package com.gdsc.wildlives.pages.splash

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gdsc.wildlives.MainActivity
import com.gdsc.wildlives.R
import com.gdsc.wildlives.data.animalData
import com.gdsc.wildlives.navigation.Screen
import com.gdsc.wildlives.pages.login.LoginViewModel
import com.gdsc.wildlives.ui.theme.colorPrimary
import com.gdsc.wildlives.ui.theme.colorSecondary
import com.gdsc.wildlives.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlin.system.exitProcess

@Composable
fun SplashScreen(
    navController: NavController,
    loginViewModel: LoginViewModel?
) {
    val scale = remember {
        Animatable(0f)
    }
    val overshootInterpolator = remember {
        OvershootInterpolator(2f)
    }
    LaunchedEffect(key1 = true) {

        loginViewModel?.signOut()

        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    overshootInterpolator.getInterpolation(it)
                }
            )
        )

        val db = Firebase.firestore

        db.collection("ANIMAL")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    animalData.add(document.toObject())

                }
                Log.d("Fetch Animal Data From Firebase", animalData.toString())
                navController.popBackStack()
                navController.navigate(Screen.LoginScreen.route)
            }
            .addOnFailureListener { exception ->
                Log.d("Fetch Animal Data From Firebase", exception.stackTraceToString())
                MainActivity().finish()
                exitProcess(1)
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf( colorPrimary, Color(0xFF96D312))
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.splash_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.weight(3f)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }

    }
}