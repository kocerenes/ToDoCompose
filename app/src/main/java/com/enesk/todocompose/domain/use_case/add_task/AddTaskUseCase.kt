package com.enesk.todocompose.domain.use_case.add_task

import com.enesk.todocompose.data.local.entity.ToDoTaskEntity
import com.enesk.todocompose.domain.repository.ToDoRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: ToDoRepository
) {
    suspend operator fun invoke(toDoTask: ToDoTaskEntity) {
        repository.addTask(toDoTaskEntity = toDoTask)
    }
}