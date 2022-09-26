package com.enesk.todocompose.domain.use_case.get_search_database

import com.enesk.todocompose.data.local.entity.ToDoTaskEntity
import com.enesk.todocompose.domain.repository.ToDoRepository
import com.enesk.todocompose.util.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchDatabaseUseCase @Inject constructor(
    private val repository: ToDoRepository
) {
    operator fun invoke(searchQuery: String): Flow<RequestState<List<ToDoTaskEntity>>> = flow {
        try {
            emit(RequestState.Loading)
            repository.searchDatabase(searchQuery = "%$searchQuery%").collect {
                emit(RequestState.Success(it))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }
}