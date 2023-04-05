package com.gdsc.wildlives.pages

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gdsc.wildlives.data.AnimalClass
import com.gdsc.wildlives.data.EndangeredClassList
import com.gdsc.wildlives.pages.encyclopedia.EncyclopediaUiState
import com.gdsc.wildlives.pages.encyclopedia.EncyclopediaViewModel
import com.gdsc.wildlives.pages.encyclopedia.component.CategoryInfoButton
import com.gdsc.wildlives.pages.encyclopedia.component.GridItemCard
import com.gdsc.wildlives.ui.theme.*

@Composable
fun EncyclopediaScreen(
    navController: NavController,
    encyclopediaViewModel: EncyclopediaViewModel?
) {
    val encyclopediaUiState = encyclopediaViewModel?.encyclopediaUiState?.collectAsState()?.value
    var firstDisplayed by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = firstDisplayed) {
        encyclopediaViewModel?.loadUserAnimalList()
        firstDisplayed = false;
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(colorPrimary, Color(0xFF96D312))
                )
            ),
    ) {
        Box(modifier = Modifier.align(Alignment.TopCenter)) {
            Column(
                modifier = Modifier.statusBarsPadding(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                GridSearchBar(
                    encyclopediaViewModel = encyclopediaViewModel,
                    encyclopediaUiState = encyclopediaUiState
                )

                Column(
                    modifier = Modifier
                        .background(ghost_white, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 15.dp, start = 25.dp,),
                        text = "MY ANIMALS",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 10.dp, horizontal = 5.dp)
                        ,
                        columns = GridCells.Adaptive(120.dp),
                        content = {
                            items(encyclopediaUiState!!.currentAnimalList) {
                                Box(
                                    modifier = Modifier
                                        .padding(5.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    GridItemCard(
                                        animalData = it,
                                        navController = navController
                                    )
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun GridSearchBar(
    encyclopediaViewModel: EncyclopediaViewModel?,
    encyclopediaUiState: EncyclopediaUiState?
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isTextEmpty by remember { mutableStateOf(true) }

    Spacer(modifier = Modifier.height(10.dp))
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .border(3.dp, ghost_white, RoundedCornerShape(30.dp))
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .height(52.dp),
            value = encyclopediaUiState!!.searchText,
            onValueChange = {
                encyclopediaViewModel!!.onSearchTextChange(it)
                isTextEmpty = it.isBlank()
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = ghost_white,
                cursorColor = ghost_white,
                disabledTextColor = ghost_white,
                errorCursorColor = ghost_white,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                errorLabelColor = ghost_white,
                leadingIconColor = ghost_white,
                disabledLabelColor = ghost_white,
                disabledLeadingIconColor = ghost_white,
                errorLeadingIconColor = ghost_white,
                trailingIconColor = ghost_white,
                disabledTrailingIconColor = ghost_white,
                errorTrailingIconColor = ghost_white,
                focusedLabelColor = ghost_white,
                unfocusedLabelColor = Color.LightGray,
                placeholderColor = ghost_white,
                disabledPlaceholderColor = ghost_white,
            ),
            singleLine = true,
            placeholder = { Text("Search") },
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .background(Color.Transparent),
                    onClick = {
                        isExpanded = !isExpanded
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Category Button"
                    )
                }
            },
            trailingIcon = {
                if (!isTextEmpty) {
                    IconButton(onClick = {
                        encyclopediaViewModel!!.onSearchTextChange("")
                        isTextEmpty = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove Icon",
                            modifier = Modifier
                                .size(22.dp)
                                .padding(end = 5.dp)
                                .background(
                                    ghost_white,
                                    RoundedCornerShape(30.dp)
                                ),
                            tint = Color.Gray
                        )
                    }
                }
            }
        )
        if(isExpanded) {
            Divider(
                color = ghost_white,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .height(150.dp)
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier
                        .padding(start = 15.dp),
                    text = "ANIMAL CLASSES",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Normal,
                    color = ghost_white
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
                            CategoryInfoButton(
                                encyclopediaViewModel = encyclopediaViewModel,
                                encyclopediaUiState = encyclopediaUiState,
                                title = AnimalClass.Mammals.name,
                            )
                        }
                        item {
                            CategoryInfoButton(
                                encyclopediaViewModel = encyclopediaViewModel,
                                encyclopediaUiState = encyclopediaUiState,
                                title = AnimalClass.Birds.name,
                            )
                        }
                        item {
                            CategoryInfoButton(
                                encyclopediaViewModel = encyclopediaViewModel,
                                encyclopediaUiState = encyclopediaUiState,
                                title = AnimalClass.Amphibians.name,
                            )
                        }
                        item {
                            CategoryInfoButton(
                                encyclopediaViewModel = encyclopediaViewModel,
                                encyclopediaUiState = encyclopediaUiState,
                                title = AnimalClass.Reptiles.name,
                            )
                        }
                        item {
                            CategoryInfoButton(
                                encyclopediaViewModel = encyclopediaViewModel,
                                encyclopediaUiState = encyclopediaUiState,
                                title = AnimalClass.Fish.name,
                            )
                        }
                        item {
                            CategoryInfoButton(
                                encyclopediaViewModel = encyclopediaViewModel,
                                encyclopediaUiState = encyclopediaUiState,
                                title = AnimalClass.Invertebrates.name
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


                Text(
                    modifier = Modifier
                        .padding(start = 15.dp),
                    text = "ENDANGERED WILDLIFE CLASS ",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Normal,
                    color = ghost_white
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
                            CategoryInfoButton(
                                encyclopediaViewModel = encyclopediaViewModel,
                                encyclopediaUiState = encyclopediaUiState,
                                title = EndangeredClassList.ONE.name,
                                isAnimalClass = false
                            )
                        }
                        item {
                            CategoryInfoButton(
                                encyclopediaViewModel = encyclopediaViewModel,
                                encyclopediaUiState = encyclopediaUiState,
                                title = EndangeredClassList.TWO.name,
                                isAnimalClass = false
                            )
                        }
                        item {
                            CategoryInfoButton(
                                encyclopediaViewModel = encyclopediaViewModel,
                                encyclopediaUiState = encyclopediaUiState,
                                title = EndangeredClassList.NONE.name,
                                isAnimalClass = false
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
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

