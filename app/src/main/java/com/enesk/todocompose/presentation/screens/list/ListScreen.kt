package com.enesk.todocompose.presentation.screens.list

import android.annotation.SuppressLint
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.enesk.todocompose.R
import com.enesk.todocompose.presentation.components.ListAppBar
import com.enesk.todocompose.presentation.ui.theme.fabBackgroundColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Scaffold(
        topBar = {
            ListAppBar()
        },
        content = {},
        floatingActionButton = {
            FabButtonInListScreen(onFabClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun FabButtonInListScreen(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.fab_button_description),
            tint = Color.White
        )

    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {})
}