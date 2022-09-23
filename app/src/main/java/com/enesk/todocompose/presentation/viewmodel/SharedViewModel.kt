package com.enesk.todocompose.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesk.todocompose.data.local.entity.ToDoTaskEntity
import com.enesk.todocompose.domain.repository.ToDoRepository
import com.enesk.todocompose.domain.use_case.add_task.AddTaskUseCase
import com.enesk.todocompose.domain.use_case.update_task.UpdateTaskUseCase
import com.enesk.todocompose.util.Action
import com.enesk.todocompose.util.Constants.MAX_TITLE_LENGTH
import com.enesk.todocompose.util.Priority
import com.enesk.todocompose.util.RequestState
import com.enesk.todocompose.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    //values for update task
    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTaskEntity>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTaskEntity>>> = _allTasks

    private val _selectedTask: MutableStateFlow<ToDoTaskEntity?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTaskEntity?> = _selectedTask

    fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect {
                _selectedTask.value = it
            }
        }
    }

    fun updateTaskFields(selectedTask: ToDoTaskEntity?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title.value = newTitle
        }
    }

    fun validateFields() = title.value.isNotEmpty() && description.value.isNotEmpty()

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTaskEntity(
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            addTaskUseCase(toDoTask = toDoTask)
        }
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTaskEntity(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            updateTaskUseCase(toDoTask = toDoTask)
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }
            Action.UPDATE -> {
                updateTask()
            }
            Action.DELETE -> {

            }
            Action.DELETE_ALL -> {

            }
            Action.UNDO -> {

            }
            else -> {

            }
        }
        this.action.value = Action.NO_ACTION
    }
}