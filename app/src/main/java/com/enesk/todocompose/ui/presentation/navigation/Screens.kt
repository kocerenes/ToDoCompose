package com.enesk.todocompose.ui.presentation.navigation

import androidx.navigation.NavHostController
import com.enesk.todocompose.util.Action
import com.enesk.todocompose.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {

    val list: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action.name}"){
            popUpTo(LIST_SCREEN){
                inclusive = true
            }
        }
    }

    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}