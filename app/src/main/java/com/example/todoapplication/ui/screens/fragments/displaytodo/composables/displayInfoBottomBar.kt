package com.example.todoapplication.ui.screens.fragments.displaytodo.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapplication.ui.screens.fragments.mylist.composables.isExpired
import com.example.todoapplication.ui.theme.Blue_1
import com.example.todoapplication.ui.theme.Green_1
import com.example.todoapplication.ui.theme.firaSansSemiBold

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayInfoBottomBar(
    onClickSave: ()->Unit,
    todoStatus: String,
    date: String
){
    val statusMaker = remember { mutableStateOf("") }
    if(todoStatus != "COMPLETED"){
        if(isExpired(date)){
            statusMaker.value = "EXPIRED"
        }else{
            statusMaker.value = todoStatus
        }
    }else{
        statusMaker.value = todoStatus
    }
    if(statusMaker.value == "PENDING"){
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth().background(Color.White)
        ) {
            Button(
                onClick = onClickSave,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Green_1
                ),
                modifier = Modifier
                    .padding(
                        bottom = 10.dp, top = 20.dp, end = 16.dp, start = 16.dp
                    )
                    .weight(1f)
                    .fillMaxWidth(),
                elevation = null
            ) {
                Text(
                    text = "Complete your task",
                    style = TextStyle(
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        fontFamily = firaSansSemiBold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}