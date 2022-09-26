package com.enesk.todocompose.domain.use_case.delete_all_task

import com.enesk.todocompose.domain.repository.ToDoRepository
import javax.inject.Inject

class DeleteAllTaskUseCase @Inject constructor(
    private val repository: ToDoRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllTasks()
    }
}