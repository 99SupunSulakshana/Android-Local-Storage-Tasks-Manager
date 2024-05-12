package com.example.todoapplication.ui.screens.fragments.mylist.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapplication.R
import com.example.todoapplication.model.Todo
import com.example.todoapplication.ui.theme.Blue_1
import com.example.todoapplication.ui.theme.Gray_600
import com.example.todoapplication.ui.theme.darkerGrotesqueBold
import com.example.todoapplication.ui.theme.firaSansNormal

@Composable
fun MyTodoListScreen(
    onItemClick: (todo:Todo)->Unit,
    onClickFilter: (item:Int)->Unit,
    todoList: List<Todo>
){
    val lazyListState = rememberLazyListState()
    val items = listOf(
        "ALL",
        "HIGH",
        "LOW",
        "MEDIUM",)

    var selectedIndex by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val rotateState by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){

        if(todoList.isEmpty()) {
            EmptyTodoScreen()
        }else{
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(top = 20.dp, bottom =50.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                state = lazyListState,
            ) {
                item {
                    Text(
                        text = "Get organized and increase your productivity.",
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                        fontFamily = firaSansNormal,
                        color = Gray_600,
                        modifier = Modifier.padding(start = 0.dp, bottom = 20.dp)
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 0.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    items[selectedIndex],
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                            onClick = { expanded = true }),
                                    textAlign = TextAlign.Justify,
                                    style = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        fontFamily = firaSansNormal,
                                        color = Blue_1
                                    )
                                )
                                Spacer(modifier = Modifier.padding(start = 5.dp))
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_drop_down),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                            onClick = {
                                                expanded = !expanded
                                            })
                                        .rotate(rotateState),
                                    tint = Blue_1
                                )
                                Column {
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false },
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .wrapContentHeight()
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color.White)
                                            .align(Alignment.CenterHorizontally)
                                    ) {
                                        items.forEachIndexed { index, s ->
                                            DropdownMenuItem(
                                                onClick = {
                                                    selectedIndex = index
                                                    expanded = false
                                                    onClickFilter(index)
                                                }) {
                                                Text(
                                                    text = s,
                                                    style = TextStyle(
                                                        fontWeight = FontWeight.W400,
                                                        fontSize = 14.sp,
                                                        fontFamily = firaSansNormal,
                                                        color = Gray_600
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                itemsIndexed(items = todoList){_, item ->
                    TodoItem(
                        title = item.name,
                        date = item.date,
                        time = item.time,
                        status = item.status,
                        priority = item.priority,
                        onClickNext = {
                            onItemClick(item)
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun EmptyTodoScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(id = R.drawable.empty),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(40.dp, 40.dp)
                .fillMaxWidth()
                .padding(top = 13.dp, end = 30.dp,start = 30.dp, bottom = 10.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))
        androidx.compose.material3.Text(
            "No data found!",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = darkerGrotesqueBold,
                color = Gray_600
            ),
            modifier = Modifier.padding(start = 0.dp, top = 0.dp, bottom = 4.dp),
            textAlign = TextAlign.Center
        )
    }
}