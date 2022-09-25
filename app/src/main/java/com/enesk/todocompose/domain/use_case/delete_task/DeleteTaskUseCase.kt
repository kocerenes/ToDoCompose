package com.enesk.todocompose.domain.use_case.delete_task

import com.enesk.todocompose.data.local.entity.ToDoTaskEntity
import com.enesk.todocompose.domain.repository.ToDoRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: ToDoRepository
) {
    suspend operator fun invoke(toDoTaskEntity: ToDoTaskEntity){
        repository.deleteTask(toDoTaskEntity = toDoTaskEntity)
    }
}