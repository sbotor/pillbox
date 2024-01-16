package com.sbcf.pillbox.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import kotlin.reflect.KMutableProperty0

@Composable
fun NumberField(
    state: KMutableProperty0<Int>,
    isEditable: Boolean,
    labelId : Int,
    maxLength: Int,
    modifier: Modifier
)
{
    var amount by remember { mutableStateOf(state.get().toString()) }

    if(amount.isNotEmpty())
        amount = state.get().toString()

    TextField(
        value = amount,
        onValueChange = {
            if(it.length <= maxLength)
            {
                amount = it
                if(amount.isNotEmpty())
                {
                    state.set(amount.trim().toInt())
                }
            }
        },
        label = { Text( stringResource(id = labelId)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        modifier = modifier,
        enabled = isEditable
    )
}