package com.example.todoapplication.ui.screens.fragments.mylist.composables

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapplication.R
import com.example.todoapplication.ui.theme.Blue_1
import com.example.todoapplication.ui.theme.Gray_200
import com.example.todoapplication.ui.theme.Gray_400
import com.example.todoapplication.ui.theme.Gray_600
import com.example.todoapplication.ui.theme.Green_1
import com.example.todoapplication.ui.theme.Red_1
import com.example.todoapplication.ui.theme.Yellow_1
import com.example.todoapplication.ui.theme.darkerGrotesqueBold
import com.example.todoapplication.ui.theme.firaSansNormal
import com.example.todoapplication.ui.theme.firaSansSemiBold
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoItem(
    title: String,
    date: String,
    time: String,
    status: String,
    priority: String,
    onClickNext: ()->Unit
) {
    val color = remember { mutableStateOf(Yellow_1) }
    val colorForPriority = remember { mutableStateOf(Yellow_1) }
    val btnText = remember { mutableStateOf("Pending") }
    val statusMaker = remember { mutableStateOf("") }
    if(status != "COMPLETED"){
        if(isExpired(date)){
            statusMaker.value = "EXPIRED"
        }else{
            statusMaker.value = status
        }
    }else{
       statusMaker.value = status
    }
    when (statusMaker.value) {
        "PENDING" -> {
            color.value = Yellow_1
            btnText.value = "Pending"
        }

        "COMPLETED" -> {
            color.value = Green_1
            btnText.value = "Completed"
        }

        "EXPIRED" -> {
            color.value = Gray_600
            btnText.value = "Expired"
        }
    }

    when (priority) {
        "HIGH" -> {
            colorForPriority.value = Red_1
        }

        "LOW" -> {
            colorForPriority.value = Green_1
        }

        "MEDIUM" -> {
            colorForPriority.value = Yellow_1
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 0.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    onClickNext()
                }
            )
            .background(
                color = Color.White, shape = RoundedCornerShape(15.dp)
            ),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Row(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(4f)
                        .padding(start = 0.dp, end = 0.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ){
                        Text(
                            title,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                fontFamily = darkerGrotesqueBold,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(start = 0.dp, top = 0.dp, bottom = 0.dp),
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row {
                            Text(
                                text = date,
                                fontWeight = FontWeight.W400,
                                fontSize = 14.sp,
                                fontFamily = firaSansNormal,
                                color = Gray_600,
                                modifier = Modifier.padding(start = 0.dp),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "| $time",
                                fontWeight = FontWeight.W400,
                                fontSize = 14.sp,
                                fontFamily = firaSansNormal,
                                color = color.value,
                                modifier = Modifier.padding(start = 0.dp),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "| $priority",
                                fontWeight = FontWeight.W400,
                                fontSize = 14.sp,
                                fontFamily = firaSansNormal,
                                color = colorForPriority.value,
                                modifier = Modifier.padding(start = 0.dp),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(2.5f)
                        .padding(start = 0.dp, end = 0.dp),
                    contentAlignment = Alignment.CenterEnd
                ){
                    OutlinedButton(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = color.value.copy(
                                alpha = 0.2f
                            )
                        ),
                        modifier = Modifier
                            .height(30.dp)
                            .wrapContentSize(),
                        border = BorderStroke(0.dp, color = Color.Transparent),
                        shape = RoundedCornerShape(9.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = btnText.value,
                            style = TextStyle(
                                color = color.value,
                                fontFamily = firaSansSemiBold,
                                fontWeight = FontWeight.W500,
                                fontSize = 12.sp
                            ),
                            maxLines = 1,
                            modifier = Modifier
                                .padding(top = 2.dp, bottom = 2.dp, start = 8.dp, end = 8.dp),
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                onClickNext()
                            }
                        )
                        .padding(start = 0.dp, end = 0.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "next",
                        modifier = Modifier
                            .size(16.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    onClickNext()
                                }
                            ),
                        tint = Gray_400
                    )
                }
            }
        }

    }
}

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
fun isExpired(date: String): Boolean {
    try {
        val just = LocalDateTime.now();
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = just.format(formatter)
        val date1 = simpleDateFormat.parse(formattedDate)
        val date2 = simpleDateFormat.parse(date)

        if(date1.equals(date2)){
            return false;
        }else if(date1.before(date2)){
            return false;
        }else if(date1.after(date2)){
            return true;
        }else{
            return false;
        }
    }catch (e: IOException){
        return false;
    }

}

