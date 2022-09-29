package com.enesk.todocompose.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

    fun readSortState() {
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

        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    fun getAllTasks() {
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

    private fun addTask() = viewModelScope.launch {
        val toDoTask = ToDoTaskEntity(
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        addTaskUseCase(toDoTask = toDoTask)

        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateTask() = viewModelScope.launch {
        val toDoTask = ToDoTaskEntity(
            id = id.value,
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        updateTaskUseCase(toDoTask = toDoTask)
    }

    private fun deleteTask() = viewModelScope.launch {
        val toDoTask = ToDoTaskEntity(
            id = id.value,
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        deleteTaskUseCase(toDoTaskEntity = toDoTask)
    }

    private fun deleteAllTasks() = viewModelScope.launch {
        deleteAllTaskUseCase()
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
        this.action.value = Action.NO_ACTION
    }
}