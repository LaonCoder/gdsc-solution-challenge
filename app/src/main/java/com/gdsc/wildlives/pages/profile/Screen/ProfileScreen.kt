package com.gdsc.wildlives.pages.profile.Screen

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gdsc.wildlives.R
import com.gdsc.wildlives.component.TopAppBarWithBack
import com.gdsc.wildlives.pages.profile.ProfileViewModel
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.pages.search.component.ClassDetailCard
import com.gdsc.wildlives.pages.search.component.SearchBar
import com.gdsc.wildlives.pages.search.component.SearchSlideSection
import com.gdsc.wildlives.ui.theme.colorPrimary
import com.gdsc.wildlives.ui.theme.dark_gray
import com.gdsc.wildlives.ui.theme.ghost_gray
import com.gdsc.wildlives.ui.theme.ghost_white
import kotlin.math.roundToInt

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel?
) {
    val profileUiState = profileViewModel?.profileUiState?.collectAsState()?.value

    LaunchedEffect(key1 = Unit) {
        profileViewModel?.loadUserProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = ghost_white),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(colorPrimary, Color(0xFF96D312))
                    )
                )
            ,
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
                    .padding(top = 5.dp, end = 10.dp)
                    .size(42.dp)
                ,
                onClick = { /* TODO : when clicked, Nav to setting page and change profile */ }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.person_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .border(
                            BorderStroke(4.dp, Color.White),
                            CircleShape
                        )
                        .padding(4.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = profileUiState!!.userName,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )  // Username
                Text(
                    text = profileUiState!!.userDescription,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 35.dp, bottom = 5.dp),
                text = "MY STATUS",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                colorPrimary,
                                Color(0xFF8CC63C)
                            )
                        ),
                        shape = RoundedCornerShape(30.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth()
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .size(100.dp)
                            .background(Color.White, RoundedCornerShape(20.dp))
                            .clip(CircleShape)
                    ) {
                        // TODO : badge image here
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoGraph,
                                contentDescription = "Level",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle( Color.White )
                                    ) {
                                        append("LEVEL ")
                                    }

                                    withStyle(
                                        style = SpanStyle( Color.White )
                                    ) {
                                        append(
                                            if (profileUiState?.currentLevel == 9) {
                                                "MAX(9)"
                                            } else {
                                                profileUiState?.currentLevel.toString()
                                            }
                                        )
                                    }
                                },
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.width(15.dp))

                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = "Points",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = buildAnnotatedString {
                                    append(numberFormat(profileUiState!!.userPoints))
                                    append("PTS")
                                },
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = when (profileUiState!!.currentLevel) {
                                1 -> "Beginner"
                                2 -> "Animal Observer"
                                3 -> "Animal Friend"
                                4 -> "Animal Advocate"
                                5 -> "Animal Protector"
                                6 -> "Animal Lover"
                                7 -> "Animal Expert"
                                8 -> "Animal Master"
                                9 -> "Animal Champion"
                                else -> ""
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color.White
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 15.dp, bottom = 15.dp, end = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = buildAnnotatedString {
                            if (profileUiState!!.currentLevel == 9) {
                                append("Reached MAX Level :)")
                            } else {
                                append("Next Level : ")
                                append(profileUiState!!.nextLevel.toString())
                                append("  ( ")
                                append(numberFormat(profileUiState!!.resPoint))
                                append(" more points to go. )")
                            }
                        },
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.White
                    )
                    
                    Box(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .width(315.dp)
                            .height(45.dp)
                            .background(Color.White, RoundedCornerShape(30.dp))
                    ) {
                        ProgressBar()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 35.dp, bottom = 5.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle( Color.Gray )
                    ) {
                        append("MY ANIMAL ")
                    }
                    append("(")
                    append("4")
                    append(")")
                },
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Normal,
                color = Color.LightGray,
            )

            if (profileUiState!!.userAnimalList.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(horizontal = 20.dp)
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Data Found",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        color = ghost_gray
                    )
                }
            }
            else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Log.d("My AnimalData", profileUiState?.userAnimalList.toString())
                    if (profileUiState!!.userAnimalList.size > 3) {
                        items(profileUiState!!.userAnimalList.subList(0, 3)) {
                            ClassDetailCard(
                                animalData = it,
                                navController = navController
                            )
                        }
                        item {
                            Box (
                                modifier = Modifier.width(80.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(40.dp),
                                    imageVector = Icons.Default.MoreHoriz,
                                    contentDescription = null,
                                    tint = ghost_gray
                                )
                            }
                        }
                    } else {
                        items(profileUiState!!.userAnimalList) {
                            ClassDetailCard(animalData = it, navController = navController)
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun ProgressBar() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var progress: Int = 75;

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .height(30.dp)
                .background(Color.LightGray)
                .width(300.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .height(30.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF0F9D58),
                                Color(0xF055CA4D)
                            )
                        )
                    )
                    .width(300.dp * progress / 100)
            )
            Text(
                text = "$progress %",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }
}


private fun numberFormat(num: Int): String {
    return when {
        num in 1000..9999 -> "${(num / 1000f).round(1)}K"
        num in 10000..99999 -> "${(num / 1000f).round(1)}K"
        num in 100000..999999 -> "${(num / 1000f).round(1)}K"
        num in 1000000..9999999 -> "${(num / 1000000f).round(1)}M"
        num in 10000000..99999999 -> "${(num / 1000000f).round(1)}M"
        num in 100000000..999999999 -> "${(num / 1000000f).round(1)}M"
        num >= 1000000000 -> "${num / 1000000000}B"
        else -> "$num"
    }
}


private fun Float.round(decimals: Int): Float {
    var multiplier = 1.0f
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}


@Composable
@Preview
fun ProfileScreenPrev() {
    ProfileScreen(navController = rememberNavController(), profileViewModel = null)
}