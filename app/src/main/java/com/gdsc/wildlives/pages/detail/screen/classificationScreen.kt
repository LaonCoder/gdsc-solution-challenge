package com.gdsc.wildlives.pages.detail.screen

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.gdsc.wildlives.BuildConfig
import com.gdsc.wildlives.R
import com.gdsc.wildlives.component.TopAppBarWithBack
import com.gdsc.wildlives.data.AnimalClass
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.data.EndangeredClassList
import com.gdsc.wildlives.pages.detail.DetailViewModel
import com.gdsc.wildlives.pages.login.LoginViewModel
import com.gdsc.wildlives.ui.theme.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import java.lang.Math.pow

@Composable
fun DetailScreen(
    animalData: AnimalData?,
    navController: NavController,
    detailViewModel: DetailViewModel?
) {
    val scrollState = rememberScrollState()

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
                                append("Ailurus fulgens")
                                append(" )")
                            },
                            color = Color.Gray,
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        EndangeredClassIcon(animalData.endangeredClass)
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
                            text = "  " + "The Red Panda is a small animal, roughly the size of a domestic cat, with reddish-brown fur and a long, bushy tail with distinctive white markings. They have round ears and a masked face, which is white with reddish-brown tear markings. They have sharp claws and semi-retractable claws that help them climb trees with ease.",
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
                        text = "Mammals"
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    SpecificationRow(
                        icon = Icons.Default.Straighten,
                        title = "LENGTH",
                        text = "51 – 63.5 cm (20.1 – 25.0 in)"
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    SpecificationRow(
                        icon = Icons.Default.Scale,
                        title = "WEIGHT",
                        text = "3.2 - 15 kg (7.1 - 33.1 lb)"
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    SpecificationRow(
                        icon = Icons.Default.Forest,
                        title = "HABITATS",
                        text = "Forest, Shrubland"
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                }

                Spacer(modifier = Modifier.height(5.dp))

                GoogleSearchButton(animalData!!.name)

                Spacer(modifier = Modifier.height(45.dp))
            }
        }

        TopAppBarWithBack(navController = navController)
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
fun EndangeredClassIcon(
    endangeredClass: String
) {
    if (endangeredClass == EndangeredClassList.ONE.name) {
        Box(
            modifier = Modifier
                .background(endangered_class_1_color, RoundedCornerShape(10.dp))
                .padding(vertical = 2.dp, horizontal = 10.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CLASS 1",
                color = Color.White,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
        }
    } else if (endangeredClass == EndangeredClassList.TWO.name) {
        Box(
            modifier = Modifier
                .background(endangered_class_2_color)
                .padding(20.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CLASS 2",
                color = Color.White,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun GoogleSearchButton(
    animalName: String
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    Button(
        modifier = Modifier
            .offset(y = -(20.dp))
            .fillMaxWidth()
        ,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp,
            disabledElevation = 0.dp
        ),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        onClick = {
            val searchUri = "https://www.google.com/search?q=$animalName"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUri))
            launcher.launch(intent)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
            ,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "Google Login Button"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = "Search On Google",
                color = Color.DarkGray,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
@Preview
fun DetailScreenPrev() {
    DetailScreen(
        animalData =
        AnimalData(
            "Red panda", "Mammal", "EN", "https://cdn.pixabay.com/photo/2020/06/13/00/41/redpanda-5292233_1280.jpg"
        ),
        navController = rememberNavController(), detailViewModel = null)
}