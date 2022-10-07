package com.enesk.todocompose.presentation.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.enesk.todocompose.R
import com.enesk.todocompose.presentation.components.PriorityDropDown
import com.enesk.todocompose.presentation.ui.theme.AlertDialogOutlineButtonsColor
import com.enesk.todocompose.presentation.ui.theme.EXTRA_SMALL_PADDING
import com.enesk.todocompose.presentation.ui.theme.LARGE_PADDING
import com.enesk.todocompose.presentation.ui.theme.MEDIUM_PADDING
import com.enesk.todocompose.presentation.ui.theme.TaskContentColor
import com.enesk.todocompose.util.Priority

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { newTitle ->
                onTitleChange(newTitle)
            },
            label = { Text(text = stringResource(id = R.string.title_text_field_label)) },
            colors = TextFieldDefaults
                .outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.TaskContentColor,
                    focusedLabelColor = MaterialTheme.colors.TaskContentColor,
                ),
            textStyle = MaterialTheme.typography.body1,
            singleLine = true
        )

        Spacer(modifier = Modifier.padding(vertical = MEDIUM_PADDING))

        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )

        Spacer(modifier = Modifier.padding(vertical = EXTRA_SMALL_PADDING))

        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { newDescription ->
                onDescriptionChange(newDescription)
            },
            colors = TextFieldDefaults
                .outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.TaskContentColor,
                    focusedLabelColor = MaterialTheme.colors.TaskContentColor
                ),
            label = { Text(text = stringResource(id = R.string.description_text_field_label)) },
            textStyle = MaterialTheme.typography.body1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskContentPreviewTurkish() {
    TaskContent(
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.MEDIUM,
        onPrioritySelected = {}
    )
}

@Preview(showBackground = true, locale = "en")
@Composable
fun TaskContentPreviewEnglish() {
    TaskContent(
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.MEDIUM,
        onPrioritySelected = {}
    )
}