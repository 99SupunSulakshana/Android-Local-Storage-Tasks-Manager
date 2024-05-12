package com.example.todoapplication.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val desc: String,
    val date: String,
    val time: String,
    val reminder: String,
    val priority: String,
    val status: String
) : Serializable
