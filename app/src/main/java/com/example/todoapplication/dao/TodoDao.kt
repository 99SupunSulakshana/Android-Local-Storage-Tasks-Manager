package com.example.todoapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapplication.model.Todo

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTodos()

    @Query("SELECT * FROM todo_table ORDER BY id DESC")
    fun readAllData() : LiveData<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE priority LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Todo>>
}