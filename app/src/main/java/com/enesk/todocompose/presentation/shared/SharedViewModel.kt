package com.enesk.todocompose.presentation.shared

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesk.todocompose.data.local.entity.ToDoTaskEntity
import com.enesk.todocompose.domain.repository.ToDoRepository
import com.enesk.todocompose.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _allTasks = MutableStateFlow<List<ToDoTaskEntity>>(emptyList())
    val allTasks: StateFlow<List<ToDoTaskEntity>> = _allTasks

    fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks.collect {
                _allTasks.value = it
            }
        }
    }
}