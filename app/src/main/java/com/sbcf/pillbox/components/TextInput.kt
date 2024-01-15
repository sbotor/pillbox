package com.sbcf.pillbox.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sbcf.pillbox.utils.validation.InputState

@Composable
fun TextInput(
    state: InputState,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    maxLength: Int? = null,
    enabled: Boolean = true
) {
    val changeGuard: (String) -> Boolean =
        if (maxLength == null) { _ -> true } else { value -> value.length <= maxLength }

    OutlinedTextField(
        value = state.value,
        onValueChange = {
            state.validation.markAsDirty()
            if (changeGuard(it)) {
                state.validation.validate(it)
                state.value = it
            }
        },
        label = label,
        isError = !state.validation.isValid,
        supportingText = {
            if (state.validation.error != null) {
                Text(text = state.validation.error!!)
            }
        }, placeholder = placeholder, modifier = modifier, enabled = enabled
    )
}