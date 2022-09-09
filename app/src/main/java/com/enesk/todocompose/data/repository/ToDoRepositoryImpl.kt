package com.enesk.todocompose.data.repository

import com.enesk.todocompose.data.local.ToDoDao
import com.enesk.todocompose.data.local.entity.ToDoTaskEntity
import com.enesk.todocompose.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao
) : ToDoRepository {

    override val getAllTasks: Flow<List<ToDoTaskEntity>> = toDoDao.getAllTasks()

    override val sortByLowPriority: Flow<List<ToDoTaskEntity>> = toDoDao.sortByLowPriority()

    override val sortByHighPriority: Flow<List<ToDoTaskEntity>> = toDoDao.sortByHighPriority()

    override fun getSelectedTask(taskId: Int): Flow<ToDoTaskEntity> {
        return toDoDao.getSelectedTask(taskId = taskId)
    }

    override suspend fun updateTask(toDoTaskEntity: ToDoTaskEntity) {
        toDoDao.updateTask(toDoTask = toDoTaskEntity)
    }

    override suspend fun addTask(toDoTaskEntity: ToDoTaskEntity) {
        toDoDao.addTask(toDoTask = toDoTaskEntity)
    }

    override suspend fun deleteTask(toDoTaskEntity: ToDoTaskEntity) {
        toDoDao.deleteTask(toDoTask = toDoTaskEntity)
    }

    override suspend fun deleteAllTasks() {
        toDoDao.deleteAllTasks()
    }

    override fun searchDatabase(searchQuery: String): Flow<List<ToDoTaskEntity>> {
        return toDoDao.searchDatabase(searchQuery = searchQuery)
    }
}