package com.example.todoapplication.ui.screens.fragments.addtodo.composables

import androidx.compose.foundation.layout.*
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapplication.R
import com.example.todoapplication.ui.theme.Blue_1
import com.example.todoapplication.ui.theme.Gray_100
import com.example.todoapplication.ui.theme.Gray_400
import com.example.todoapplication.ui.theme.Gray_600
import com.example.todoapplication.ui.theme.Gray_900
import com.example.todoapplication.ui.theme.darkerGrotesqueBold
import com.example.todoapplication.ui.theme.firaSansNormal
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddTodoScreen(
    name: MutableState<TextFieldValue>,
    desc: MutableState<TextFieldValue>,
    date: MutableState<TextFieldValue>,
    time: MutableState<TextFieldValue>,
    reminder: MutableState<TextFieldValue>,
    showNameError: MutableState<Boolean>,
    showDescError: MutableState<Boolean>,
    showDateError: MutableState<Boolean>,
    showTimeError: MutableState<Boolean>,
    showReminderError: MutableState<Boolean>,
    selected: String,
    setSelected: (selected: String) -> Unit,
) {

    val mYear: Int
    val mMonth: Int
    val mDay: Int


    val nameErrorMsg = "provide a valid task name"
    val descErrorMsg = "provide a valid task description"
    val dateErrorMsg = "provide a valid task due date"
    val timeErrorMSg = "provide a valid task due time"
    val reminderErrorMsg = "provide a valid reminder count"


    val mContext = LocalContext.current


    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }
    val mDate = remember { mutableStateOf("") }

    if (!mDate.value.isNullOrEmpty()) {
        date.value = TextFieldValue(mDate.value)
    }

    if (!mTime.value.isNullOrEmpty()) {
        time.value = TextFieldValue(mTime.value)
    }


    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            val timeString = "$mHour:$mMinute"
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val localTime = LocalTime.parse(timeString, formatter)
            val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val formattedTime = localTime.format(outputFormatter)
            mTime.value = formattedTime;
            println("Formatted time: $formattedTime")
        }, mHour, mMinute, false
    )

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )

    val todoPriorityTypes = listOf("HIGH", "LOW", "MEDIUM")
    fun getPrioritytype(item: String) = when (item) {
        "HIGH" -> "High"
        "LOW" -> "Low"
        "MEDIUM" -> "Medium"
        else -> ""
    }

    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    val items = listOf(
        "EveryDay",
        "On Time",
        "None"
    )

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painterResource(id = R.drawable.add_image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(height = 150.dp, width = 150.dp)
                    .fillMaxWidth()
                    .padding(top = 13.dp, end = 30.dp,start = 30.dp, bottom = 10.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = {
                        name.value = it
                        showNameError.value =
                            name.value.text.isEmpty()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                    textStyle = TextStyle(Color.Black, fontSize = 18.sp),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text(
                            text = "Enter task name",
                            color = Gray_400,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                fontFamily = firaSansNormal
                            )
                        )
                    },
                    trailingIcon = {

                    },
                    keyboardOptions =  KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        cursorColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        disabledIndicatorColor = Color.LightGray,
                    )
                )
                if (showNameError.value) {
                    Text(
                        text = nameErrorMsg,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 25.dp, end = 10.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                OutlinedTextField(
                    value = desc.value,
                    onValueChange = {
                        desc.value = it
                        showDescError.value =
                            desc.value.text.isEmpty()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                    textStyle = TextStyle(Color.Black, fontSize = 18.sp),
                    singleLine = false,
                    maxLines = 10,
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text(
                            text = "Enter task description",
                            color = Gray_400,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                fontFamily = firaSansNormal
                            )
                        )
                    },
                    keyboardOptions =  KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        cursorColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        disabledIndicatorColor = Color.LightGray,
                    )
                )
                if (showDescError.value) {
                    Text(
                        text = descErrorMsg,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 25.dp, end = 10.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                OutlinedTextField(
                    readOnly = true,
                    enabled = false,
                    value = date.value,
                    onValueChange = {
                        date.value = it
                        showDateError.value =
                            date.value.text.isEmpty()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                mDatePickerDialog.show()
                            }
                        )
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                    textStyle = TextStyle(Color.Black, fontSize = 18.sp),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text(
                            text = "Enter task due date",
                            color = Gray_400,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                fontFamily = firaSansNormal
                            )
                        )
                    },
                    trailingIcon = {
                        Image(
                            painterResource(id = R.drawable.calendar),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        mDatePickerDialog.show()
                                    }
                                )
                                .size(width = 15.dp, height = 15.dp)
                                .padding()
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        cursorColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        disabledIndicatorColor = Color.LightGray,
                    )
                )
                if (showDateError.value) {
                    Text(
                        text = dateErrorMsg,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 25.dp, end = 10.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                OutlinedTextField(
                    enabled = false,
                    readOnly = true,
                    value = time.value,
                    onValueChange = {
                        time.value = it
                        showTimeError.value =
                            time.value.text.isEmpty()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                mTimePickerDialog.show()
                            }
                        )
                        .wrapContentHeight()
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                    textStyle = TextStyle(Color.Black, fontSize = 18.sp),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text(
                            text = "Enter task due time",
                            color = Gray_400,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                fontFamily = firaSansNormal
                            )
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.time),
                            contentDescription = "List",
                            modifier = Modifier
                                .padding()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        mTimePickerDialog.show()
                                    }
                                )
                                .size(20.dp),
                            tint = Color.Black
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        cursorColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        disabledIndicatorColor = Color.LightGray,
                    )
                )
                if (showTimeError.value) {
                    Text(
                        text = timeErrorMSg,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 25.dp, end = 10.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                OutlinedTextField(
                    enabled = false,
                    readOnly = true,
                    value = reminder.value,
                    onValueChange = {
                        reminder.value = it
                        showReminderError.value =
                            reminder.value.text.isEmpty()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                expanded = !expanded
                            }
                        )
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                    textStyle = TextStyle(Color.Black, fontSize = 18.sp),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text(
                            text = "Enter task reminder count",
                            color = Gray_400,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                fontFamily = firaSansNormal
                            )
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                tint = Color.Black,
                                contentDescription = "Dropdown"
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        cursorColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        disabledIndicatorColor = Color.LightGray,
                    )
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items.forEach { option ->
                        DropdownMenuItem(onClick = {
                            text = option
                            reminder.value = TextFieldValue(text)
                            expanded = false
                        }) {
                            Text(text = option)
                        }
                    }
                }
                if (showReminderError.value) {
                    Text(
                        text = reminderErrorMsg,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 25.dp, end = 10.dp)
                    )
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Enter task priority",
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        fontFamily = firaSansNormal
                    )
                )
            }
            FlowRow(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 10.dp)
                    .fillMaxWidth()
            ) {
                todoPriorityTypes.forEach { item ->
                    Row(
                        modifier = Modifier.padding(start = 2.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selected == item,
                            onClick = {
                                setSelected(item)
                            },
                            enabled = true,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Blue_1,
                                unselectedColor = Gray_100
                            ),
                            modifier = Modifier
                                .size(16.dp)
                                .padding(start = 2.dp)
                        )
                        ClickableText(
                            AnnotatedString(getPrioritytype(item = item)),
                            //AnnotatedString(text = item),
                            style = TextStyle(
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = firaSansNormal,
                                color = Gray_900
                            ),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 15.dp),
                            onClick = {
                                setSelected(item)
                            }
                        )
                    }
                }
            }
        }
    }
}

