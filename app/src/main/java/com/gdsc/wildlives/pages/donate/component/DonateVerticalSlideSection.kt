package com.gdsc.wildlives.pages.donate


import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gdsc.wildlives.pages.detail.component.GoogleSearchButton
import com.gdsc.wildlives.pages.donate.component.WebSiteLinkCard
import com.gdsc.wildlives.pages.encyclopedia.EncyclopediaUiState
import com.gdsc.wildlives.pages.encyclopedia.EncyclopediaViewModel
import com.gdsc.wildlives.ui.theme.*


@Composable
fun DonateVerticalSlideSection(
    donateViewModel: DonateViewModel?,
    donateUiState: DonateUiState?,
    navController: NavController,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 350.dp)
            .fillMaxWidth()
            .height(900.dp)
            .background(ghost_white, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            items(donateUiState!!.websiteInfoList) {
                WebSiteLinkCard(it)
            }

            item {
                Column() {
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp, start = 5.dp),
                        text = "To find more organizations...",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray,
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    GoogleSearchButton(searchText = "Wildlife protection organizations")
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}


@Composable
@Preview
fun DonateVerticalSlideSectionPrev() {
    DonateVerticalSlideSection(
        donateViewModel = null,
        donateUiState = null,
        navController = rememberNavController(),
        modifier = Modifier,
    )
}