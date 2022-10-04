package com.enesk.todocompose.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesk.todocompose.data.local.entity.ToDoTaskEntity
import com.enesk.todocompose.data.repository.DataStoreRepository
import com.enesk.todocompose.domain.repository.ToDoRepository
import com.enesk.todocompose.domain.use_case.add_task.AddTaskUseCase
import com.enesk.todocompose.domain.use_case.delete_all_task.DeleteAllTaskUseCase
import com.enesk.todocompose.domain.use_case.delete_task.DeleteTaskUseCase
import com.enesk.todocompose.domain.use_case.get_all_tasks.GetAllTasksUseCase
import com.enesk.todocompose.domain.use_case.get_search_database.GetSearchDatabaseUseCase
import com.enesk.todocompose.domain.use_case.get_selected_task.GetSelectedTaskUseCase
import com.enesk.todocompose.domain.use_case.update_task.UpdateTaskUseCase
import com.enesk.todocompose.util.Action
import com.enesk.todocompose.util.Constants.MAX_TITLE_LENGTH
import com.enesk.todocompose.util.Priority
import com.enesk.todocompose.util.RequestState
import com.enesk.todocompose.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val getSelectedTaskUseCase: GetSelectedTaskUseCase,
    private val getSearchDatabaseUseCase: GetSearchDatabaseUseCase,
    private val deleteAllTaskUseCase: DeleteAllTaskUseCase,
    private val dataStoreRepository: DataStoreRepository,
    private val repository: ToDoRepository
) : ViewModel() {

    var action by mutableStateOf(Action.NO_ACTION)
        private set

    //values for update task
    var id by mutableStateOf(0)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var priority by mutableStateOf(Priority.LOW)
        private set

    var searchAppBarState by mutableStateOf(SearchAppBarState.CLOSED)
        private set
    var searchTextState by mutableStateOf("")
        private set

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTaskEntity>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTaskEntity>>> = _allTasks

    private val _searchedTasks =
        MutableStateFlow<RequestState<List<ToDoTaskEntity>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTaskEntity>>> = _searchedTasks

    private val _selectedTask: MutableStateFlow<ToDoTaskEntity?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTaskEntity?> = _selectedTask

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    val lowPriorityTasks: StateFlow<List<ToDoTaskEntity>> =
        repository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val highPriorityTasks: StateFlow<List<ToDoTaskEntity>> =
        repository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    init {
        getAllTasks()
        readSortState()
    }

    private fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map {
                        Priority.valueOf(it)
                    }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }
    }

    fun persistSortState(priority: Priority) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.persistSortState(priority = priority)
    }

    fun searchDatabase(searchQuery: String) {
        getSearchDatabaseUseCase(searchQuery = searchQuery).onEach { result ->
            when (result) {
                is RequestState.Loading -> {
                    _searchedTasks.value = result
                }
                is RequestState.Success -> {
                    _searchedTasks.value = result
                }
                is RequestState.Error -> {
                    _searchedTasks.value = result
                }
            }
        }.launchIn(viewModelScope)

        searchAppBarState = SearchAppBarState.TRIGGERED
    }

    private fun getAllTasks() {
        getAllTasksUseCase().onEach { result ->
            when (result) {
                is RequestState.Loading -> {
                    _allTasks.value = result
                }
                is RequestState.Success -> {
                    _allTasks.value = result
                }
                is RequestState.Error -> {
                    _allTasks.value = result
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSelectedTask(taskId: Int) {
        getSelectedTaskUseCase(taskId = taskId).onEach { selectedTask ->
            _selectedTask.value = selectedTask
        }.launchIn(viewModelScope)
    }

    fun updateTaskFields(selectedTask: ToDoTaskEntity?) {
        if (selectedTask != null) {
            id = selectedTask.id
            title = selectedTask.title
            description = selectedTask.description
            priority = selectedTask.priority
        } else {
            id = 0
            title = ""
            description = ""
            priority = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title = newTitle
        }
    }

    fun validateFields() = title.isNotEmpty() && description.isNotEmpty()

    private fun addTask() = viewModelScope.launch {
        val toDoTask = ToDoTaskEntity(
            title = title,
            description = description,
            priority = priority
        )
        addTaskUseCase(toDoTask = toDoTask)

        searchAppBarState = SearchAppBarState.CLOSED
    }

    private fun updateTask() = viewModelScope.launch {
        val toDoTask = ToDoTaskEntity(
            id = id,
            title = title,
            description = description,
            priority = priority
        )
        updateTaskUseCase(toDoTask = toDoTask)
    }

    private fun deleteTask() = viewModelScope.launch {
        val toDoTask = ToDoTaskEntity(
            id = id,
            title = title,
            description = description,
            priority = priority
        )
        deleteTaskUseCase(toDoTaskEntity = toDoTask)
    }

    private fun deleteAllTasks() = viewModelScope.launch {
        deleteAllTaskUseCase()
    }

    fun handleDatabaseActions(action: Action) {
        Log.d("shared", "Triggered")
        when (action) {
            Action.ADD -> {
                addTask()
            }
            Action.UPDATE -> {
                updateTask()
            }
            Action.DELETE -> {
                deleteTask()
            }
            Action.DELETE_ALL -> {
                deleteAllTasks()
            }
            Action.UNDO -> {
                addTask()
            }
            else -> {
                //no-action
            }
        }
    }

    fun updateAction(newAction: Action) {
        action = newAction
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun updatePriority(newPriority: Priority) {
        priority = newPriority
    }

    fun updateAppBarState(newState: SearchAppBarState) {
        searchAppBarState = newState
    }

    fun updateSearchText(newText: String) {
        searchTextState = newText
    }
}