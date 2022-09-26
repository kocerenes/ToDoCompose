package com.enesk.todocompose.domain.use_case.get_selected_task

import com.enesk.todocompose.data.local.entity.ToDoTaskEntity
import com.enesk.todocompose.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSelectedTaskUseCase @Inject constructor(
    private val repository: ToDoRepository
) {
    operator fun invoke(taskId: Int): Flow<ToDoTaskEntity> = flow {
        repository.getSelectedTask(taskId = taskId).collect {
            emit(it)
        }
    }
}