package com.gdsc.wildlives.pages.donate.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gdsc.wildlives.R
import com.gdsc.wildlives.pages.donate.DonateVerticalSlideSection
import com.gdsc.wildlives.pages.donate.DonateViewModel
import com.gdsc.wildlives.ui.theme.ghost_white

@Composable
fun DonateScreen(
    navController: NavController,
    donateViewModel: DonateViewModel?
) {
    val donateUiState = donateViewModel?.donateUiState?.collectAsState()?.value
    var firstDisplayed by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = firstDisplayed) {
        donateViewModel?.loadWebsiteData()
        firstDisplayed = false
    }

    if (!firstDisplayed) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = ghost_white),
        ) {
            Box(modifier = Modifier.align(Alignment.TopCenter)) {
                Image(
                    painter = painterResource(id = R.drawable.donate_background_image3),
                    contentDescription = "donation background image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = -(120.dp))
                        .zIndex(-1f)
                )

                Column(
                    modifier = Modifier.statusBarsPadding(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.height(160.dp))

                    Column(
                        modifier = Modifier.padding(start = 15.dp, bottom = 15.dp)
                    ) {
                        Text(
                            modifier = Modifier.offset(y = 10.dp),
                            text = "Help",
                            fontSize = 60.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                        )
                        Text(
                            text = "endangered wildlife",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        Text(
                            modifier = Modifier.padding(end = 15.dp),
                            text = "If you're interested in wildlife conservation, look for wildlife conservation organizations you like and try sponsoring them.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White.copy(alpha = 0.6f),
                        )
                    }
                }
            }

            DonateVerticalSlideSection(
                donateViewModel = donateViewModel,
                donateUiState = donateUiState,
                navController = navController,
                modifier = Modifier
            )
        }
    }
}


@Composable
@Preview
fun DonateScreenPrev() {
    DonateScreen(
        navController = rememberNavController(),
        donateViewModel = null
    )
}