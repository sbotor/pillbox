package com.sbcf.pillbox.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.sbcf.pillbox.utils.Dimens

@Composable
fun ListItemSpacer(height: Dp = Dimens.PaddingLarge) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    )
}