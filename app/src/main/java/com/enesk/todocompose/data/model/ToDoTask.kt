package com.enesk.todocompose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enesk.todocompose.util.Constants.DATABASE_TABLE
import com.enesk.todocompose.util.Priority

@Entity(tableName = DATABASE_TABLE)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)
