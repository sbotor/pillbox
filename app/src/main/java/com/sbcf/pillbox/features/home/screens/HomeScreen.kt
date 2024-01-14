package com.sbcf.pillbox.features.home.screens

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.features.home.viewmodels.HomeViewModel
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel()
) {
    Scaffold { padding ->
        Text(text = vm.getTextTest(), modifier = Modifier.scaffoldedContent(padding))
    }
}