package com.sbcf.pillbox.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sbcf.pillbox.R
import com.sbcf.pillbox.utils.Length

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    onClear: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = { if (it.length <= Length.SearchField.MaxLength) onValueChange(it) },
        label = label,
        trailingIcon = {
            IconButton(onClick = onClear) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = stringResource(id = R.string.search)
                )
            }
        }, leadingIcon = {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.close)
                )
            }
        }, modifier = modifier, singleLine = true
    )
}
