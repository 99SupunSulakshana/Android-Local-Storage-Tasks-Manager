package com.example.todoapplication.ui.screens.addTodoActivity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.app.NotificationManagerCompat
import com.example.todoapplication.model.Todo
import com.example.todoapplication.reminder.NotificationReceiver
import com.example.todoapplication.reminder.TodoReceiver
import com.example.todoapplication.reminder.description
import com.example.todoapplication.reminder.notificationID
import com.example.todoapplication.reminder.titleExtra
import com.example.todoapplication.ui.screens.fragments.addtodo.composables.AddTodoScreen
import com.example.todoapplication.ui.screens.fragments.addtodo.composables.BottomBar
import com.example.todoapplication.ui.screens.fragments.addtodo.composables.MyListTopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.Calendar
import java.util.Date

class AddDataActivity : AppCompatActivity() {

    private val viewModel: AddDataVM by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(Color.White)
            systemUiController.setNavigationBarColor(Color.White)
            val scaffoldState: ScaffoldState = rememberScaffoldState()
            val coroutineScope: CoroutineScope = rememberCoroutineScope()
            val name = remember { mutableStateOf(TextFieldValue()) }
            val desc = remember { mutableStateOf(TextFieldValue()) }
            val date = remember { mutableStateOf(TextFieldValue()) }
            val time = remember { mutableStateOf(TextFieldValue()) }
            val reminder = remember { mutableStateOf(TextFieldValue()) }
            val priority = remember { mutableStateOf(TextFieldValue()) }
            val (selected, setSelected) = remember { mutableStateOf("") }
            val status = remember { mutableStateOf("PENDING") }

            val showErrorName = remember { mutableStateOf(false) }
            val showErrorDesc = remember { mutableStateOf(false) }
            val showErrorDate = remember { mutableStateOf(false) }
            val showErrorTime = remember { mutableStateOf(false) }
            val showErrorReminder = remember { mutableStateOf(false) }

            fun validate(): Boolean {
                showErrorName.value = name.value.text.isEmpty()
                showErrorDesc.value = desc.value.text.isEmpty()
                showErrorDate.value = date.value.text.isEmpty()
                showErrorTime.value = time.value.text.isEmpty()
                showErrorReminder.value = reminder.value.text.isEmpty()

                return !showErrorName.value && !showErrorDesc.value && !showErrorDate.value && !showErrorTime.value && !showErrorReminder.value
            }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Scaffold(
                    topBar = {
                        MyListTopAppBar(onBackNav = {
                            finish()
                        })
                    },
                    content = { innerPadding ->
                        Box(
                            modifier = Modifier.padding(innerPadding),
                        ) {
                            AddTodoScreen(
                                name = name,
                                desc = desc,
                                date = date,
                                time = time,
                                reminder = reminder,
                                showNameError = showErrorName,
                                showDescError = showErrorDesc,
                                showDateError = showErrorDate,
                                showTimeError = showErrorTime,
                                showReminderError = showErrorReminder,
                                selected = selected,
                                setSelected = setSelected
                            )
                        }
                    },
                    bottomBar = {
                        BottomBar(
                            onClickSave = {
                                Log.e(
                                    "AddTodoFragment",
                                    "name - ${name.value.text}, desc - ${desc.value.text}, date = ${date.value.text}, time = ${time.value.text}, reminder = ${reminder.value.text}, priority = ${selected}, status = ${status.value}"
                                )
                                if (validate()) {
                                    if (selected.isNotEmpty()) {
                                        coroutineScope.launch {
                                            val todo = Todo(
                                                id = 0,
                                                name = name.value.text,
                                                desc = desc.value.text,
                                                date = date.value.text,
                                                time = time.value.text,
                                                reminder = reminder.value.text,
                                                priority = selected,
                                                status = status.value
                                            )
                                            viewModel.addTodo(
                                                todo
                                            )
                                            if (reminder.value.text == "EveryDay") {
                                                setReminderForEveryDay(24 * 60 * 60 * 1000)
                                            } else if (reminder.value.text == "On Time") {
                                                if (checkNotificationPermissions(this@AddDataActivity)) {
                                                    scheduleNotification(date = date.value.text, time = time.value.text, titleFor = name.value.text, messageFor = desc.value.text)
                                                }
                                            } else { }
                                            Toast.makeText(
                                                this@AddDataActivity,
                                                "Successfully Added!",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    } else {
                                        Toast.makeText(
                                            this@AddDataActivity,
                                            "please, add your priority!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        )
                    }
                )
            }
        }
        }


    @RequiresApi(Build.VERSION_CODES.O)
    fun checkNotificationPermissions(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            val isEnabled = notificationManager.areNotificationsEnabled()

            if (!isEnabled) {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                context.startActivity(intent)

                return false
            }
        } else {
            val areEnabled = NotificationManagerCompat.from(context).areNotificationsEnabled()

            if (!areEnabled) {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                context.startActivity(intent)

                return false
            }
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification(date: String, time: String, titleFor: String, messageFor: String) {
        val intent = Intent(this, NotificationReceiver::class.java)
        intent.putExtra(titleExtra, titleFor)
        intent.putExtra(description, messageFor)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val pendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(
                    this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            } else {
                PendingIntent.getBroadcast(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val timeClick = System.currentTimeMillis();
        val tenSeconds = 1000 * 10;
        val timeForNotify = getTime(date, time)
        val selectedTime = "${getSelectedDate(date)} ${getSelectedTime(time)}"
        val timeDifference = setTime(selectedTime)
        println("timeDifference: $timeDifference")
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            timeClick + (timeDifference * 1000),
            pendingIntent
        )
        showAlert(timeForNotify, titleFor, messageFor)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setTime(selectedDateAndTime: String): Int{
        try{
            val formatter = DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 4)
                .appendLiteral('-')
                .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE)
                .appendLiteral('-')
                .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE)
                .appendLiteral(' ')
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .appendLiteral(':')
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .toFormatter()

            val selectedDateTime = LocalDateTime.parse(selectedDateAndTime, formatter)

            val selectedInstant = selectedDateTime.atZone(ZoneId.systemDefault()).toInstant()

            val currentInstant = Instant.now()

            val differenceInSeconds = (selectedInstant.epochSecond - currentInstant.epochSecond)

            println("Difference in seconds: $differenceInSeconds")
            return differenceInSeconds.toInt();
        } catch (e: Exception) {
            println("Error parsing date: ${e.message}")
            return 1000 * 100
        }
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: $title\nMessage: $message\nAt: ${dateFormat.format(date)} ${timeFormat.format(date)}"
            )
            .setPositiveButton("Okay") { _, _ -> finish()}
            .show()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTime(date:String, time:String): Long {
        val reformat = formatTimeString(time)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val local = LocalTime.parse(reformat, formatter)
        val formattedTime = local.format(formatter)
        val localTime = LocalTime.parse(formattedTime, formatter)
        val hour = localTime.hour
        val minute = localTime.minute
        println("Hour: $hour, Minute: $minute")

        val formatterDate = DateTimeFormatter.ofPattern("d/M/yyyy")
        val localDate = LocalDate.parse(date, formatterDate)
        val year = localDate.year
        val month = localDate.monthValue
        val day = localDate.dayOfMonth

        println("Year: $year, Month: $month, Day: $day")

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getSelectedTime(time:String):String{
        val reformat = formatTimeString(time)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val local = LocalTime.parse(reformat, formatter)
        val formattedTime = local.format(formatter)
        val localTime = LocalTime.parse(formattedTime, formatter)
        val hour = localTime.hour
        val minute = localTime.minute
        println("Hour: $hour, Minute: $minute")
        return "$hour:$minute:00";
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getSelectedDate(date:String):String{
        val formatterDate = DateTimeFormatter.ofPattern("d/M/yyyy")
        val localDate = LocalDate.parse(date, formatterDate)
        val year = localDate.year
        val month = localDate.monthValue
        val day = localDate.dayOfMonth

        println("Year: $year, Month: $month, Day: $day")
        return "$year-$month-$day"
    }

    private fun formatTimeString(timeString: String): String {
        val parts = timeString.split(":")
        val hour = parts[0]
        val minute = parts.getOrNull(1)?.toIntOrNull()?.let { "%02d".format(it) } ?: ""
        return "$hour:$minute"
    }
    private fun setReminderForEveryDay(interval: Long) {
        val triggerMills = System.currentTimeMillis() + interval
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, TodoReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, notificationID, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, triggerMills,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(
            this,
            "Reminder Set for every day Successfully!",
            Toast.LENGTH_LONG
        ).show()
    }



}


