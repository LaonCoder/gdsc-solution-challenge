package com.gdsc.wildlives.pages.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
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
import coil.compose.AsyncImage
import com.gdsc.wildlives.data.AnimalData

@Composable
fun EndangeredClassDetailCard(
    animalData: AnimalData
) {
    val isClicked by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .height(140.dp)
            .width(120.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {

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
fun EndangeredClassDetailCardPrev() {
    EndangeredClassDetailCard(animalData =
        AnimalData(
            "Red panda", "Mammal", "EN", "https://cdn.pixabay.com/photo/2020/06/13/00/41/redpanda-5292233_1280.jpg"
        )
    )
}