package com.gdsc.wildlives.pages.donate.component

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdsc.wildlives.R
import com.gdsc.wildlives.data.WebsiteData
import com.gdsc.wildlives.ui.theme.colorPrimary
import com.gdsc.wildlives.ui.theme.ghost_white

@Composable
fun WebSiteLinkCard(
    websiteInfo: WebsiteData?
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    var isExpanded by remember { mutableStateOf(false) }

    Column() {
        Row(
          modifier = Modifier
              .height(38.dp)
              .background(
                  brush = Brush.horizontalGradient(
                      colors = listOf(colorPrimary, Color(0xFF96D312))
                  ),
                  shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
              ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = websiteInfo!!.name,
                modifier = Modifier.padding(start = 20.dp),
                color = ghost_white,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .clickable {
                        val searchUri = websiteInfo!!.mainPageUrl
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUri))
                        launcher.launch(intent)
                    }
                    .padding(end = 20.dp)
                    .background(Color.Transparent, RoundedCornerShape((20.dp)))
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "GO",
                    modifier = Modifier.padding(end = 5.dp),
                    color = ghost_white,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold)
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    modifier = Modifier.size(18.dp),
                    contentDescription = "Navigate to Sample",
                    tint = ghost_white
                )
            }
        }


        if (!isExpanded) {
            Row(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                    )
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 15.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(6f),
                    textAlign = TextAlign.Start,
                    text =
                        if (websiteInfo!!.introduction.length <= 50) websiteInfo.introduction
                        else websiteInfo.introduction.substring(0, 51) +  "...",
                    color = Color.LightGray
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .size(28.dp),
                    onClick = { isExpanded = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        modifier = Modifier.size(30.dp),
                        contentDescription = "Expand Explanation",
                        tint = Color.Gray
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                    )
                    .padding(10.dp)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = websiteInfo!!.introduction,
                    textAlign = TextAlign.Justify,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(15.dp))

                DonateLinkButton(donatePageUrl = websiteInfo!!.donationPageUrl)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 5.dp)
                ) {
                    Spacer(modifier = Modifier.weight(6f))
                    IconButton(
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .size(28.dp),
                        onClick = { isExpanded = false }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            modifier = Modifier.size(30.dp),
                            contentDescription = "Close Explanation",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DonateLinkButton(
    donatePageUrl: String
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
        ,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp,
            disabledElevation = 0.dp
        ),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colorPrimary),
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(donatePageUrl))
            launcher.launch(intent)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Default.Handshake,
                contentDescription = "Handshake Icon",
                tint = ghost_white
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sponsor this organization",
                color = ghost_white,
                fontSize = 18.sp
            )
        }
    }
}