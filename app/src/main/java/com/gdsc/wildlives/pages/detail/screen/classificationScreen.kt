package com.gdsc.wildlives.pages.detail.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gdsc.wildlives.R
import com.gdsc.wildlives.data.AnimalClass
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.data.IucnRedList
import com.gdsc.wildlives.pages.detail.DetailViewModel
import com.gdsc.wildlives.pages.login.LoginViewModel
import com.gdsc.wildlives.ui.theme.dark_gray
import com.gdsc.wildlives.ui.theme.ghost_gray
import java.lang.Math.pow

@Composable
fun DetailScreen(
    animalData: AnimalData?,
    navController: NavController,
    detailViewModel: DetailViewModel
) {
    val scrollState = rememberScrollState()

    Box {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .graphicsLayer {
                        alpha = 1f - (scrollState.value.toFloat() / scrollState.maxValue)
                        translationY = 0.3f * scrollState.value
                    }
                ,
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawWithContent {
                            drawContent()
                            drawRect(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black),
                                    startY = size.height / 5,
                                    endY = size.height
                                )
                            )
                        }
                    ,
                    model = if (animalData?.imageUrl.isNullOrEmpty()) "https://www.example.com/image.jpg" else animalData?.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = animalData!!.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 60.sp,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(600.dp))
        }
    }
}

@Composable
@Preview
fun DetailScreenPrev() {

}