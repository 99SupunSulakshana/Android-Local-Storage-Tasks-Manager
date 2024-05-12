package com.example.todoapplication.ui.screens.fragments.displaytodo.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.todoapplication.ui.theme.Blue_1
import com.example.todoapplication.ui.theme.Gray_200
import com.example.todoapplication.ui.theme.Red_1
import com.example.todoapplication.ui.theme.firaSansSemiBold

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RemoveDialogBox(
    setShowDialog: (Boolean) -> Unit,
    onCancelClick: () -> Unit,
    onClickRemove: () -> Unit
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        onDismissRequest = { setShowDialog(false) }) {
        Surface(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Are you want to remove this todo?",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        fontFamily = firaSansSemiBold,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(start = 20.dp, bottom = 24.dp, end = 20.dp),
                    textAlign = TextAlign.Center
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                ) {
                    Button(
                        onClick = onCancelClick,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Gray_200
                        ),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(44.dp)
                    ) {
                        Text(
                            text = "Cancel",
                            style = TextStyle(
                                fontWeight = FontWeight.W600,
                                fontSize = 14.sp,
                                fontFamily = firaSansSemiBold,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(start = 18.dp, end = 18.dp)
                        )
                    }

                    Button(
                        onClick = { onClickRemove()},
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Red_1
                        ),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .padding(
                                start = 8.dp
                            )
                            .height(44.dp)
                    ) {
                        Text(
                            text = "Remove",
                            style = TextStyle(
                                fontWeight = FontWeight.W600,
                                fontSize = 14.sp,
                                fontFamily = firaSansSemiBold,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(start = 18.dp, end = 18.dp)
                        )
                    }
                }
            }
        }
    }
}