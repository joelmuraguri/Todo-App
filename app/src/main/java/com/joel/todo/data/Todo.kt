package com.joel.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Todo_Table")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val title : String,
    val description : String,
    val timeStamp : Long = 0L
)
