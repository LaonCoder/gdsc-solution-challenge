package com.gdsc.wildlives.pages.detail.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.gdsc.wildlives.component.TopAppBarWithBack
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.data.animalData
import com.gdsc.wildlives.pages.detail.DetailViewModel
import com.gdsc.wildlives.pages.detail.component.EndangeredClassIcon
import com.gdsc.wildlives.pages.detail.component.GoogleSearchButton
import com.gdsc.wildlives.ui.theme.*

@Composable
fun DetailScreen(
    animalData: AnimalData?,
    navController: NavController,
    detailViewModel: DetailViewModel?
) {
    val scrollState = rememberScrollState()
    val detailUiState = detailViewModel?.detailUiState?.collectAsState()?.value

    Box {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .graphicsLayer {
                        alpha =
                            (1f - 0.6 * (scrollState.value.toFloat() / scrollState.maxValue)).toFloat()
                        translationY = 0.3f * scrollState.value
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawWithContent {
                            drawContent()
                            drawRect(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.White),
                                    startY = 2 * size.height / 3,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .offset(y = -(30.dp))
                ) {
                    Text(
                        modifier = Modifier.offset(y = -(30.dp)),
                        text = animalData!!.name,
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = -(25.dp))
                        ,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.offset(y = -(3.dp)),
                            text = buildAnnotatedString {
                                append("( ")
                                append(animalData!!.scientificName)
                                append(" )")
                            },
                            color = Color.Gray,
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        EndangeredClassIcon(animalData!!.endangeredClass)
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Column(
                        modifier = Modifier
                            .offset(y = -(20.dp))
                            .background(
                                color = ghost_white,
                                shape = RoundedCornerShape(30.dp)
                            )
                            .border(
                                width = 3.dp,
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF92D109),
                                        colorPrimary
                                    )
                                ),
                                shape = RoundedCornerShape(30.dp)
                            ),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 25.dp, top = 15.dp),
                            text = "BRIEF DESCRIPTION",
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Normal,
                            color = colorPrimary
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 15.dp, start = 25.dp, end = 25.dp)
                            ,
                            text = "  " + animalData!!.description,
                            style = MaterialTheme.typography.body1,
                            color = Color.DarkGray
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .offset(y = -(30.dp))
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    colorPrimary,
                                    Color(0xFF7CB109)
                                )
                            ), RoundedCornerShape(30.dp)
                        )
                    ,
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        modifier = Modifier
                            .padding(start = 25.dp),
                        text = "FEATURES",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                    )

                    Divider(
                        color = ghost_white,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 15.dp, start = 25.dp, end = 25.dp)
                            .width(1.dp)
                    )

                    SpecificationRow(
                        icon = Icons.Default.Pets,
                        title = "ANIMAL CLASS",
                        text = animalData!!.animalClass
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    SpecificationRow(
                        icon = Icons.Default.Straighten,
                        title = "LENGTH",
                        text = animalData!!.length
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    SpecificationRow(
                        icon = Icons.Default.Scale,
                        title = "WEIGHT",
                        text = animalData!!.weight
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    SpecificationRow(
                        icon = Icons.Default.Forest,
                        title = "HABITATS",
                        text = animalData!!.habitats
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                }

                Spacer(modifier = Modifier.height(5.dp))

                GoogleSearchButton(animalData!!.name)

                Spacer(modifier = Modifier.height(45.dp))
            }
        }

        TopAppBarWithBack(
            navController = navController,
            animalData = animalData,
            detailViewModel = detailViewModel,
            detailUiState = detailUiState,
            isClassified = false
        )
    }
}

@Composable
fun SpecificationRow(
    icon: ImageVector,
    title: String,
    text: String,
) {
    Row(
        modifier = Modifier
            .padding(start = 30.dp)
        ,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(38.dp),
            imageVector = icon,
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column() {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                modifier = Modifier.offset(y = -(2.dp)),
                text = text,
                style = MaterialTheme.typography.subtitle1,
                color = ghost_white
            )
        }
    }
}


@Composable
@Preview
fun DetailScreenPrev() {
    DetailScreen(
        animalData = animalData[0],
        navController = rememberNavController(), detailViewModel = null)
}