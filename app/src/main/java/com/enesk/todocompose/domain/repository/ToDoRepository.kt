package com.enesk.todocompose.domain.repository

import com.enesk.todocompose.data.local.entities.ToDoTaskEntity
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    val getAllTasks: Flow<List<ToDoTaskEntity>>
    val sortByLowPriority: Flow<List<ToDoTaskEntity>>
    val sortByHighPriority: Flow<List<ToDoTaskEntity>>
    fun getSelectedTask(taskId: Int): Flow<ToDoTaskEntity>
    suspend fun updateTask(toDoTaskEntity: ToDoTaskEntity)
    suspend fun addTask(toDoTaskEntity: ToDoTaskEntity)
    suspend fun deleteTask(toDoTaskEntity: ToDoTaskEntity)
    suspend fun deleteAllTasks()
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTaskEntity>>
}