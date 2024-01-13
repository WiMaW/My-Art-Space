package com.wioletamwrobel.myartspace

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.wioletamwrobel.myartspace.ui.theme.Shapes

object Dialog {


    @Composable
    fun CreateDialog(
        icon: @Composable () -> Unit,
        title: String,
        dialogText: @Composable () -> Unit,
        onConfirmButtonClicked: () -> Unit,
        onDismissButtonClicked: () -> Unit,
    ) {
        AlertDialog(
            icon = icon,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            text = dialogText,
            onDismissRequest = onDismissButtonClicked,
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClicked
                ) {
                    Text(
                        stringResource(id = R.string.confirm_button),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissButtonClicked
                ) {
                    Text(
                        stringResource(id = R.string.cancel_button),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DialogTextField(
        value: String,
        onValueChange: (String) -> Unit,
        isError: Boolean = false,
        modifier: Modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small))
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = onValueChange,
            shape = Shapes.small,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.onSecondary,
                focusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                errorBorderColor = MaterialTheme.colorScheme.error,
            ),
            label = {
                if (isError) {
                    Text(stringResource(R.string.wrong_input))
                } else {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { }
            ),
            modifier = modifier
                .padding(dimensionResource(R.dimen.padding_extra_small))
        )
    }
}