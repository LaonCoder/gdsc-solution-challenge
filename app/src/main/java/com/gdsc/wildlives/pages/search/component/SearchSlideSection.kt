package com.gdsc.wildlives.pages.search.component

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gdsc.wildlives.data.AnimalClass
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.data.IucnRedList
import com.gdsc.wildlives.data.animalData
import com.gdsc.wildlives.pages.search.SearchUiState
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import okhttp3.internal.wait
import java.lang.Thread.yield
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SearchSlideSection(
    searchViewModel: SearchViewModel?,
    searchUiState: SearchUiState?,
    navController: NavController,
    modifier: Modifier,
) {
    var firstDisplayed by remember { mutableStateOf(true) }
    val pagerState = rememberPagerState( pageCount = 5, initialOffscreenLimit = 3, infiniteLoop = true )

    // Initial SlideBar Condition
    LaunchedEffect(key1 = firstDisplayed) {
        searchViewModel?.onAnimalClassChange(AnimalClass.Mammals.name)
        searchViewModel?.onRedListChange(IucnRedList.EX.name)
        searchViewModel?.createTodaysAnimalList()
        firstDisplayed = false
    }

    // Initial Pager Condition
    LaunchedEffect(Unit) {
        while(true) {
            yield()
            delay(2000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 350.dp)
            .background(ghost_white, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        if ( !firstDisplayed ) {
            Column(
                modifier = Modifier
                    .height(240.dp)
                    .padding(horizontal = 20.dp),
            ) {
                Text(
                    text = "Today's Animal",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 20.dp,
                        ),
                    itemSpacing = 20.dp
                ) { pageIndex ->
                    TodaysAnimalDetailCard(
                        modifier = Modifier
                            .graphicsLayer {
                                val pageOffset =
                                    calculateCurrentOffsetForPage(pageIndex).absoluteValue

                                lerp(
                                    start = 0.85f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                ).also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }

                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            },
                        animalData = searchUiState!!.todaysAnimalList[pageIndex]
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row() {
                Text(
                    text = "Classes",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = null,
                    tint = ghost_gray
                )
                LazyRow(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(30.dp))
                    ,
                    contentPadding = PaddingValues(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    item {
                        ClassInfoButton(
                            searchViewModel = searchViewModel,
                            searchUiState = searchUiState,
                            title = AnimalClass.Mammals.name,
                            textColor = Color.White,
                            backgroundColor = colorPrimary
                        )
                    }

                    item {
                        ClassInfoButton(
                            searchViewModel = searchViewModel,
                            searchUiState = searchUiState,
                            title = AnimalClass.Birds.name,
                            textColor = Color.White,
                            backgroundColor = colorPrimary
                        )
                    }

                    item {
                        ClassInfoButton(
                            searchViewModel = searchViewModel,
                            searchUiState = searchUiState,
                            title = AnimalClass.Amphibians.name,
                            textColor = Color.White,
                            backgroundColor = colorPrimary
                        )
                    }

                    item {
                        ClassInfoButton(
                            searchViewModel = searchViewModel,
                            searchUiState = searchUiState,
                            title = AnimalClass.Reptiles.name,
                            textColor = Color.White,
                            backgroundColor = colorPrimary
                        )
                    }

                    item {
                        ClassInfoButton(
                            searchViewModel = searchViewModel,
                            searchUiState = searchUiState,
                            title = AnimalClass.Fish.name,
                            textColor = Color.White,
                            backgroundColor = colorPrimary
                        )
                    }

                    item {
                        ClassInfoButton(
                            searchViewModel = searchViewModel,
                            searchUiState = searchUiState,
                            title = AnimalClass.Invertebrates.name,
                            textColor = Color.White,
                            backgroundColor = colorPrimary
                        )
                    }
                }
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    tint = ghost_gray
                )
            }

            Spacer(modifier = Modifier.height(5.dp))


            if (searchUiState!!.currentAnimalClassList.isNullOrEmpty()) {
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
                    Log.d("AnimalData", searchUiState?.currentAnimalClassList.toString())
                    if (searchUiState!!.currentAnimalClassList.size > 3) {
                        items(searchUiState!!.currentAnimalClassList.subList(0, 3)) {
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
                        items(searchUiState!!.currentAnimalClassList) {
                            ClassDetailCard(animalData = it, navController = navController)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "IUCN Red List",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp)
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = null,
                    tint = ghost_gray
                )
                LazyRow(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(30.dp))
                    ,
                    contentPadding = PaddingValues(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    item {
                        RedListInfoButton( searchViewModel = searchViewModel, searchUiState = searchUiState,
                            title = IucnRedList.EX.name,
                            textColor = Color.White,
                            backgroundColor = iucn_ex_color,
                        )
                    }

                    item {
                        RedListInfoButton( searchViewModel = searchViewModel,  searchUiState = searchUiState,
                            title = IucnRedList.EW.name,
                            textColor = Color.White,
                            backgroundColor = iucn_ew_color
                        )
                    }

                    item {
                        RedListInfoButton( searchViewModel = searchViewModel,  searchUiState = searchUiState,
                            title = IucnRedList.CR.name,
                            textColor = Color.White,
                            backgroundColor = iucn_cr_color,
                        )
                    }

                    item {
                        RedListInfoButton( searchViewModel = searchViewModel,  searchUiState = searchUiState,
                            title = IucnRedList.EN.name,
                            textColor = Color.White,
                            backgroundColor = iucn_en_color,
                        )
                    }

                    item {
                        RedListInfoButton( searchViewModel = searchViewModel,  searchUiState = searchUiState,
                            title = IucnRedList.VU.name,
                            textColor = Color.White,
                            backgroundColor = iucn_vu_color,
                        )
                    }

                    item {
                        RedListInfoButton( searchViewModel = searchViewModel,  searchUiState = searchUiState,
                            title = IucnRedList.NT.name,
                            textColor = Color.White,
                            backgroundColor = iucn_nt_color,
                        )
                    }

                    item {
                        RedListInfoButton( searchViewModel = searchViewModel,  searchUiState = searchUiState,
                            title = IucnRedList.LC.name,
                            textColor = Color.White,
                            backgroundColor = iucn_lc_color,
                        )
                    }

                    item {
                        RedListInfoButton( searchViewModel = searchViewModel,  searchUiState = searchUiState,
                            title = IucnRedList.DD.name,
                            textColor = Color.White,
                            backgroundColor = iucn_dd_color,
                        )
                    }

                    item {
                        RedListInfoButton( searchViewModel = searchViewModel,  searchUiState = searchUiState,
                            title = IucnRedList.NE.name,
                            textColor = Color.LightGray,
                            backgroundColor = iucn_ne_color,
                        )
                    }
                }
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    tint = ghost_gray
                )
            }

            if (searchUiState!!.currentRedList.isNullOrEmpty()) {
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
                    if (searchUiState!!.currentRedList.size > 3) {
                        items(searchUiState!!.currentRedList.subList(0, 3)) {
                            RedListDetailCard(animalData = it)
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
                        items(searchUiState!!.currentRedList) {
                            RedListDetailCard(animalData = it)
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun SearchSlideSectionPrev() {
    SearchSlideSection(
        null, null, rememberNavController(), Modifier
    )
}