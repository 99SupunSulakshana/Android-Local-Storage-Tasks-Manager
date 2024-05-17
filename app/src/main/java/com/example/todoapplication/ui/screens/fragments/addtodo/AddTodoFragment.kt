package com.example.todoapplication.ui.screens.fragments.addtodo

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.media.audiofx.BassBoost
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.model.Todo
import com.example.todoapplication.reminder.TodoReceiver
import com.example.todoapplication.ui.screens.fragments.addtodo.composables.AddTodoScreen
import com.example.todoapplication.ui.screens.fragments.addtodo.composables.BottomBar
import com.example.todoapplication.ui.screens.fragments.addtodo.composables.MyListTopAppBar
import com.example.todoapplication.ui.screens.fragments.displaytodo.composables.RemoveDialogBox
import com.example.todoapplication.ui.screens.fragments.mylist.composables.MyTodoListScreen
import com.example.todoapplication.ui.screens.fragments.mylist.composables.TopAppBar
import com.example.todoapplication.ui.screens.splashscreen.SplashVM
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import com.example.todoapplication.reminder.NotificationReceiver


class AddTodoFragment : Fragment() {

    private val viewModel: AddTodoViewModel by viewModels()
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var calendar: Calendar
    val notificationID = 121
    val channelID = "channel1"
    val titleExtra = "titleExtra"
    val messageExtra = "messageExtra"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createNotificationChannel()
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
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
                                findNavController().popBackStack()
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
                                                //    setReminder(date.value.text, time.value.text)
                                                    if (checkNotificationPermissions(requireContext())) {
                                                        // Schedule a notification
                                                        scheduleNotification(date = date.value.text, time = time.value.text)
                                                    }
                                                } else {
                                                    cancelReminder()
                                                }
                                                Toast.makeText(
                                                    requireContext(),
                                                    "Successfully Added!",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                findNavController().popBackStack()
                                            }
                                        } else {
                                            Toast.makeText(
                                                requireContext(),
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
    }

    private fun createNotificationChannel(){
        Log.e("AddTodoScreen", "create the channel")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name: CharSequence = "todoandroidReminderChannel"
            val description = "Channel for reminder manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("todoappid", name, importance)
            channel.description = description

            val notificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setReminder(date: String, time: String) {
        Log.e("AddTodoScreen", "start reminder")
        val reformat = formatTimeString(time)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val local = LocalTime.parse(reformat, formatter)
        val formattedTime = local.format(formatter)
        val timeString = formattedTime
        val localTime = LocalTime.parse(timeString, formatter)
        val hour = localTime.hour
        val minute = localTime.minute
        println("Hour: $hour, Minute: $minute")

        val formatterDate = DateTimeFormatter.ofPattern("d/M/yyyy")
        val localDate = LocalDate.parse(date, formatterDate)
        val year = localDate.year
        val month = localDate.monthValue
        val day = localDate.dayOfMonth

        println("Year: $year, Month: $month, Day: $day")

        calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = day
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        alarmManager = requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireActivity(), TodoReceiver::class.java)
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                requireActivity(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getBroadcast(requireActivity(), 0, intent, PendingIntent.FLAG_MUTABLE)
        }
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(requireContext(), "Reminder Set Successfully!", Toast.LENGTH_LONG).show()
    }

    fun formatTimeString(timeString: String): String {
        val parts = timeString.split(":")
        val hour = parts[0]
        val minute = parts.getOrNull(1)?.toIntOrNull()?.let { "%02d".format(it) } ?: ""
        return "$hour:$minute"
    }

    private fun setReminderForEveryDay(interval: Long) {
        val triggerMills = System.currentTimeMillis() + interval
        calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        alarmManager = requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireActivity(), TodoReceiver::class.java)
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                requireActivity(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getBroadcast(requireActivity(), 0, intent, PendingIntent.FLAG_MUTABLE)
        }
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, triggerMills,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(
            requireContext(),
            "Reminder Set for every day Successfully!",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun cancelReminder() {
        Log.e("AddTodoScreen", "cancel reminder")
        alarmManager = requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireActivity(), TodoReceiver::class.java)
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_MUTABLE)
        }
        alarmManager.cancel(pendingIntent)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification(date: String, time: String) {
        Log.e("AddTodoScreen", "start reminder")
        val intent = Intent(requireContext(), NotificationReceiver::class.java)
        val title = "title"
        val message = "message"

        // Add title and message as extras to the intent
        intent.putExtra("titleExtra", title)
        intent.putExtra("messageExtra", message)

        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                requireContext(),
                notificationID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getBroadcast(requireContext(), notificationID, intent, PendingIntent.FLAG_MUTABLE)
        }

        // Get the AlarmManager service
        val alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager

        // Get the selected time and schedule the notification
        val timeForNotiry = getTime(date, time)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeForNotiry,
            pendingIntent
        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTime(date:String, time:String): Long {
        // Get selected time from TimePicker and DatePicker
        val reformat = formatTimeString(time)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val local = LocalTime.parse(reformat, formatter)
        val formattedTime = local.format(formatter)
        val timeString = formattedTime
        val localTime = LocalTime.parse(timeString, formatter)
        val hour = localTime.hour
        val minute = localTime.minute
        println("Hour: $hour, Minute: $minute")

        val formatterDate = DateTimeFormatter.ofPattern("d/M/yyyy")
        val localDate = LocalDate.parse(date, formatterDate)
        val year = localDate.year
        val month = localDate.monthValue
        val day = localDate.dayOfMonth

        println("Year: $year, Month: $month, Day: $day")

        // Create a Calendar instance and set the selected date and time
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)


        return calendar.timeInMillis
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



}