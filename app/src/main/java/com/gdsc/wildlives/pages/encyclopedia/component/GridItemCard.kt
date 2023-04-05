package com.gdsc.wildlives.pages.encyclopedia.component


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.navigation.Screen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

@Composable
fun GridItemCard(
    navController: NavController,
    animalData: AnimalData
) {
    val isClicked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .height(120.dp)
            .width(120.dp)
            .clip(RoundedCornerShape(30.dp))
            .clickable {
                val passingAnimalData = animalData.copy(imageUrl = URLEncoder.encode(animalData.imageUrl, "UTF-8"))
                val animalDataJson = Json.encodeToString(passingAnimalData)
                Log.d("animalJson", animalDataJson)
                navController.navigate(
                    route = Screen.DetailScreen.route + "?animal=${animalDataJson}"
                )
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height / 3,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                }
            ,
            model = if (animalData.imageUrl.isNullOrEmpty()) "https://www.example.com/image.jpg" else animalData.imageUrl,
            contentDescription = animalData.name + " image",
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 10.dp, bottom = 5.dp, end = 5.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = animalData.name.replaceFirstChar { it.uppercase() },
                color = Color.White
            )
        }
    }
}


@Composable
@Preview
fun GridItemCardPrev() {
    GridItemCard(
        animalData =
        AnimalData(
            "Red panda", "Mammal", "EN", "https://cdn.pixabay.com/photo/2020/06/13/00/41/redpanda-5292233_1280.jpg"
        ),
        navController = rememberNavController()
    )
}