package com.example.todoapplication.ui.screens.fragments.addtodo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.model.Todo
import com.example.todoapplication.ui.screens.fragments.addtodo.composables.AddTodoScreen
import com.example.todoapplication.ui.screens.fragments.addtodo.composables.BottomBar
import com.example.todoapplication.ui.screens.fragments.addtodo.composables.MyListTopAppBar
import com.example.todoapplication.ui.screens.fragments.displaytodo.composables.RemoveDialogBox
import com.example.todoapplication.ui.screens.fragments.mylist.composables.MyTodoListScreen
import com.example.todoapplication.ui.screens.fragments.mylist.composables.TopAppBar
import com.example.todoapplication.ui.screens.splashscreen.SplashVM
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class AddTodoFragment : Fragment() {

    private val viewModel: AddTodoViewModel by viewModels()


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
                                        Log.e("AddTodoFragment","name - ${name.value.text}, desc - ${desc.value.text}, date = ${date.value.text}, time = ${time.value.text}, reminder = ${reminder.value.text}, priority = ${selected}, status = ${status.value}")
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


}