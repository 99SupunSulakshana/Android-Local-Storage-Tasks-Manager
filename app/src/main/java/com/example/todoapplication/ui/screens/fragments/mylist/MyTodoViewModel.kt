package com.example.todoapplication.ui.screens.fragments.mylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.database.TodoDatabase
import com.example.todoapplication.model.Todo
import com.example.todoapplication.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyTodoViewModel(
    application: Application
): AndroidViewModel(application) {

    private var readAllData: LiveData<List<Todo>>
    val getTodoList: LiveData<List<Todo>> get() = readAllData

    private val repository: TodoRepository
    init {
        val userDao = TodoDatabase.getDatabase(application).todoDoa()
        repository = TodoRepository(userDao)
        readAllData = repository.readAllData
    }

    fun deleteAll(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllTodos()
        }
    }

    fun searchTodo(query:String):LiveData<List<Todo>>{
        readAllData = repository.searchTodo(query)
        return repository.searchTodo(query)
    }


}