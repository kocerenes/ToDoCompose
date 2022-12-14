package com.enesk.todocompose.util

object Constants {
    //room database
    const val DATABASE_TABLE = "todo_table"
    const val DATABASE_NAME = "todo_database"

    //navigation compose
    const val LIST_SCREEN = "list/{action}"
    const val TASK_SCREEN = "task/{taskId}"
    const val SPLASH_SCREEN = "splash"
    const val SPLASH_SCREEN_DELAY = 1000L

    // navigation keys
    const val LIST_ARGUMENT_KEY = "action"
    const val TASK_ARGUMENT_KEY = "taskId"

    //limitation
    const val MAX_TITLE_LENGTH = 20

    //DataStore
    const val PREFERENCE_NAME = "todo_preferences"
    const val PREFERENCE_KEY = "sort_state"
}