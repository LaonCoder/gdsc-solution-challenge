package com.gdsc.wildlives.pages.search.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import coil.compose.AsyncImage
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.data.animalData
import com.gdsc.wildlives.pages.search.SearchUiState
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.ui.theme.ghost_white
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerScope
import java.util.*
import kotlin.collections.List


@Composable
fun TodaysAnimalDetailCard(
    modifier: Modifier,
    animalData: AnimalData
) {
    val isClicked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .height(220.dp)
            .width(240.dp)
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
            horizontalArrangement = Arrangement.Start
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
fun TodaysAnimalDetailCardPrev() {
    TodaysAnimalDetailCard(
        modifier = Modifier,
        animalData = animalData[0]
    )
}