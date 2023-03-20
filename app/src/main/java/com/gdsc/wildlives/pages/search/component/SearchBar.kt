package com.gdsc.wildlives.pages.search.component

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.pages.search.SearchUiState
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.ui.theme.colorPrimary
import com.gdsc.wildlives.ui.theme.dark_gray
import com.gdsc.wildlives.ui.theme.ghost_gray
import com.gdsc.wildlives.ui.theme.ghost_white

@Composable
fun SearchBar(
    searchViewModel: SearchViewModel?,
    searchUiState: SearchUiState?
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isTextEmpty by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(vertical = 15.dp, horizontal = 15.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Surface(
            elevation = 6.dp,
            shape = RoundedCornerShape(30.dp),
        ) {
            Column {
                Row() {
                    if (!isExpanded) {
                        IconButton(
                            onClick = { isExpanded = !isExpanded },
                            modifier = Modifier.size(52.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(28.dp),
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Button",
                                tint = Color.Gray
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = expandHorizontally (
                            expandFrom = Alignment.End,
                        ),
                        exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut()
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                                .height(52.dp)
                            ,
                            value = searchUiState!!.searchText,
                            onValueChange = {
                                searchViewModel!!.onSearchTextChange(it)
                                isTextEmpty = it.isBlank()
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                textColor = if (isExpanded) Color.DarkGray else Color.Transparent,
                                cursorColor = if (isExpanded) Color.LightGray else Color.Transparent,
                                disabledTextColor = if (isExpanded) Color.LightGray else Color.Transparent,
                                errorCursorColor = if (isExpanded) Color.LightGray else Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                errorLabelColor = if (isExpanded) Color.DarkGray else Color.Transparent,
                                leadingIconColor = Color.Gray,
                                disabledLabelColor = Color.LightGray,
                                disabledLeadingIconColor = Color.LightGray,
                                errorLeadingIconColor = Color.LightGray,
                                trailingIconColor = Color.Gray,
                                disabledTrailingIconColor = Color.LightGray,
                                errorTrailingIconColor = Color.Gray,
                                focusedLabelColor = if (isExpanded) Color.LightGray else Color.Transparent,
                                unfocusedLabelColor = if (isExpanded) Color.LightGray else Color.Transparent,
                                placeholderColor = if (isExpanded) Color.Gray else Color.Transparent,
                                disabledPlaceholderColor = if (isExpanded) Color.Gray else Color.Transparent
                            ),
                            singleLine = true,
                            placeholder = { Text("Search") },
                            leadingIcon = {
                                IconButton(
                                    modifier = Modifier
                                        .padding(start = 3.dp, end = 10.dp)
                                        .background(ghost_white, RoundedCornerShape(30.dp))
                                    ,
                                    onClick = {
                                        isExpanded = false
                                        isTextEmpty = true
                                        searchViewModel!!.onSearchTextChange("")
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        imageVector = Icons.Default.ArrowForwardIos,
                                        contentDescription = "Search Button"
                                    )
                                }
                            },
                            trailingIcon = {
                                if (!isTextEmpty) {
                                    IconButton(onClick = {
                                        searchViewModel!!.onSearchTextChange("")
                                        isTextEmpty = true
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Remove Icon",
                                            modifier = Modifier
                                                .size(22.dp)
                                                .background(
                                                    colorPrimary,
                                                    RoundedCornerShape(30.dp)
                                                )
                                                .shadow(elevation = 4.dp, RoundedCornerShape(10.dp))
                                            ,
                                            tint = ghost_white
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
                if(!isTextEmpty && isExpanded && searchUiState!!.isSearching) {
                    Spacer(modifier = Modifier.height(3.dp))
                    Divider (
                        color = colorPrimary,
                        modifier = Modifier
                            .height(2.dp)
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .alpha(0.5f)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(120.dp)
                            .background(
                                ghost_white,
                                RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                            )
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = colorPrimary,
                            strokeWidth = 3.dp
                        )
                    }
                } else if (!isTextEmpty && isExpanded && !searchUiState!!.isSearching) {
                    Spacer(modifier = Modifier.height(3.dp).shadow(4.dp))
                    Divider (
                        color = colorPrimary,
                        modifier = Modifier
                            .height(2.dp)
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                ghost_white,
                                RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                            )
                            .heightIn(0.dp, 500.dp)
                            .padding(20.dp)
                            .zIndex(10f),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                    ) {
                        if (searchUiState!!.animalList.isNullOrEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .height(50.dp)
                                    ,
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = Color.Gray,
                                                    fontWeight = FontWeight.Normal
                                                )
                                            ) {
                                                append("No results matching ")
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    dark_gray,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            ) {
                                                append(
                                                    if (searchUiState!!.searchText.length > 20)
                                                        searchUiState!!.searchText.substring(0, 21) + "..."
                                                    else
                                                        searchUiState!!.searchText
                                                )
                                            }
                                        },
                                        style = MaterialTheme.typography.subtitle1,
                                        modifier = Modifier,
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        else {
                            Log.d("getAnimal (animalList)", searchUiState!!.animalList.toString())
                            items(searchUiState!!.animalList) { animalData ->
                                animalListItem(animalData = animalData)
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun animalListItem(animalData: AnimalData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp, 150.dp)
    ) {
        Row() {
            AsyncImage(
                modifier = Modifier.size(100.dp),
                model = if (animalData.imageUrl.isNullOrEmpty()) "https://www.example.com/image.jpg" else animalData.imageUrl,
                contentDescription = animalData.name + " image",
                contentScale = ContentScale.Crop
            )
            Column() {
                Text(text = animalData.name)
                Text(text = animalData.endangeredClass)
                Text(text = animalData.animalClass)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchBarPrev() {
    SearchBar(
        searchViewModel = null,
        searchUiState = null
    )
}