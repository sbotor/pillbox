package com.sbcf.pillbox.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sbcf.pillbox.utils.Dimens

@Composable
fun ListItemSpacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.PaddingBig)
    )
}