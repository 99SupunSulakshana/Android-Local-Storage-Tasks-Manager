package com.example.todoapplication.ui.screens.fragments.addtodo

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


class AddTodoFragment : Fragment() {

    private val viewModel: AddTodoViewModel by viewModels()
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var calendar: Calendar


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(Color.White)
                systemUiController.setNavigationBarColor(Color.White)

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
                val showErrorPriority = remember { mutableStateOf(false) }

                fun validate(): Boolean {
                    showErrorName.value = name.value.text.isEmpty()
                    showErrorDesc.value = desc.value.text.isEmpty()
                    showErrorDate.value = date.value.text.isEmpty()
                    showErrorTime.value = time.value.text.isEmpty()
                    showErrorReminder.value = reminder.value.text.isEmpty()
                    showErrorPriority.value = priority.value.text.isEmpty()

                    return !showErrorName.value && !showErrorDesc.value && !showErrorDate.value && !showErrorTime.value && !showErrorPriority.value && showErrorReminder.value
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
                                        setReminderForEveryDay(24*60*60*1000)
                                    } else if (reminder.value.text == "On Time") {
                                        setReminder(time.value.text)
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
                            )
                        }
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setReminder(time: String) {
        Log.e("AddTodoScreen", "start reminder")
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val local = LocalTime.parse(time, formatter)
        val formattedTime = local.format(formatter)
        val timeString = formattedTime
        val localTime = LocalTime.parse(timeString, formatter)
        val hour = localTime.hour
        val minute = localTime.minute
        println("Hour: $hour, Minute: $minute")
        calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), TodoReceiver::class.java)
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
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(requireContext(), "Reminder Set Successfully!", Toast.LENGTH_LONG).show()
    }

    private fun setReminderForEveryDay(interval: Long){
        val triggerMills = System.currentTimeMillis() + interval
        calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), TodoReceiver::class.java)
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
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, triggerMills,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(requireContext(), "Reminder Set for every day Successfully!", Toast.LENGTH_LONG).show()
    }

    private fun cancelReminder() {
        Log.e("AddTodoScreen", "cancel reminder")
        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), TodoReceiver::class.java)
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


}