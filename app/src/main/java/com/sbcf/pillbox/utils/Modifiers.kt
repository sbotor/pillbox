package com.sbcf.pillbox.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier

object Modifiers {
    fun scaffoldedContent(padding: PaddingValues) = Modifier
        .fillMaxSize()
        .padding(padding)
}