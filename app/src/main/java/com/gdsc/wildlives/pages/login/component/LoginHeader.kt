package com.gdsc.wildlives.pages.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gdsc.wildlives.R
import com.gdsc.wildlives.ui.theme.colorPrimary

@Composable
fun LoginHeader() {
    Surface(
        color = colorPrimary
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.auth_background_image),
                contentDescription = "login_bg",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier.padding(bottom = 40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row() {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.splash_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.weight(5f)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}