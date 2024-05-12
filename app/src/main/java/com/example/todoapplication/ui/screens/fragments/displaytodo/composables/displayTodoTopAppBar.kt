package com.example.todoapplication.ui.screens.fragments.displaytodo.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapplication.R
import com.example.todoapplication.ui.theme.Gray_600
import com.example.todoapplication.ui.theme.darkerGrotesqueBold

@Composable
fun TopBarDisplayScreen(
    onBackNav: () -> Unit,
    onInfoRemove: () -> Unit
) {
    androidx.compose.material.TopAppBar(
        content = {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back_ios),
                    contentDescription = "List",
                    modifier = Modifier
                        .padding()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onBackNav() }
                        )
                        .weight(0.9f)
                        .size(20.dp),
                    tint = Gray_600
                )
                Text(
                    "Todo Details",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        fontFamily = darkerGrotesqueBold,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp, top = 0.dp, bottom = 5.dp)
                        .weight(8.5f),
                    textAlign = TextAlign.Start
                )

                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "List",
                        modifier = Modifier
                            .padding()
                            .weight(1f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onInfoRemove() }
                            )
                            .size(24.dp),
                        tint = Color.Red
                    )

            }
        },
        backgroundColor = Color.White,
        contentColor = Color.White,
        elevation = 10.dp
    )
}