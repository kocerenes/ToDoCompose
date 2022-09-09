package com.enesk.todocompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enesk.todocompose.data.local.entity.ToDoTaskEntity

@Database(
    entities = [ToDoTaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ToDoTaskDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}