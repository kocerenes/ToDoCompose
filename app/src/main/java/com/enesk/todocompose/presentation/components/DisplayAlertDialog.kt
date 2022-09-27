package com.enesk.todocompose.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.enesk.todocompose.R
import com.enesk.todocompose.presentation.ui.theme.AlertDialogButtonTextColor
import com.enesk.todocompose.presentation.ui.theme.AlertDialogButtonsColor
import com.enesk.todocompose.presentation.ui.theme.AlertDialogOutlineButtonsColor
import com.enesk.todocompose.presentation.ui.theme.OUTLINED_TEXT_BUTTON_BORDER

@Composable
fun DisplayAlertdialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesDialog: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesDialog()
                        closeDialog()
                    },
                    border = BorderStroke(
                        OUTLINED_TEXT_BUTTON_BORDER,
                        color = MaterialTheme.colors.AlertDialogOutlineButtonsColor
                    ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.AlertDialogButtonsColor
                    )
                ) {
                    Text(text = stringResource(id = R.string.alert_dialog_confirm_button))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        closeDialog()
                    },
                    border = BorderStroke(
                        OUTLINED_TEXT_BUTTON_BORDER,
                        color = MaterialTheme.colors.AlertDialogOutlineButtonsColor
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colors.AlertDialogButtonTextColor
                    )
                ) {
                    Text(text = stringResource(id = R.string.alert_dialog_dismiss_button))
                }
            },
            onDismissRequest = {
                closeDialog()
            }
        )
    }
}