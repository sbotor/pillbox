package com.sbcf.pillbox.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import com.sbcf.pillbox.utils.validation.InputState

@Composable
fun TextInput(
    state: InputState,
    label: @Composable () -> Unit,
) {
    TextField(value = state.value,
        onValueChange = {
            state.validation.markAsDirty()
            state.validation.validate(it)
            state.value = it
        },
        label = label,
        isError = !state.validation.isValid,
        supportingText = {
            if (state.validation.error != null) {
                Text(text = state.validation.error!!)
            }
        })
}