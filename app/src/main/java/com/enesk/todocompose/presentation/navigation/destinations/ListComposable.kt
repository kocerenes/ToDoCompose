package com.enesk.todocompose.presentation.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.enesk.todocompose.presentation.screens.list.ListScreen
import com.enesk.todocompose.presentation.shared.SharedViewModel
import com.enesk.todocompose.util.Constants.LIST_ARGUMENT_KEY
import com.enesk.todocompose.util.Constants.LIST_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) {
        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}