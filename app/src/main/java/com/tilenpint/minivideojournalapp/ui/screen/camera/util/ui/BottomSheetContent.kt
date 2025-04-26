package com.tilenpint.minivideojournalapp.ui.screen.camera.util.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.tilenpint.minivideojournalapp.R
import com.tilenpint.minivideojournalapp.ui.theme.MiniVideoJournalAppTheme
import com.tilenpint.minivideojournalapp.util.FullScreenPreview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    showBottomSheer: Boolean,
    fieldValue: String,
    fieldChanged: (String) -> Unit,
    saveVideo: () -> Unit
) {
    if (!showBottomSheer) return

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = saveVideo
    ) {
        BottomContent(
            fieldValue = fieldValue,
            fieldChanged = fieldChanged,
            saveVideo = saveVideo
        )
    }
}

@Composable
private fun ColumnScope.BottomContent(
    fieldValue: String,
    fieldChanged: (String) -> Unit,
    saveVideo: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        value = fieldValue,
        onValueChange = fieldChanged,
        label = { Text(stringResource(R.string.add_desc)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            saveVideo()
        })
    )

    Button(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        onClick = saveVideo
    ) {
        Text(text = stringResource(R.string.save))
    }
}

@Composable
@FullScreenPreview
fun BottomSheetEmpty() {
    MiniVideoJournalAppTheme {
        Column {
            BottomContent(
                fieldValue = "",
                fieldChanged = {},
                saveVideo = {})
        }
    }
}

@Composable
@FullScreenPreview
fun BottomSheetDesc() {
    MiniVideoJournalAppTheme {
        Column {
            BottomContent(
                fieldValue = "Test desc",
                fieldChanged = {},
                saveVideo = {})
        }
    }
}
