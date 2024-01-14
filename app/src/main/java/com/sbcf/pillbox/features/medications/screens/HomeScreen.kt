package com.sbcf.pillbox.features.medications.screens

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.features.medications.viewmodels.HomeViewModel
import com.sbcf.pillbox.utils.Modifiers

@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel()
)
{
    Scaffold {padding ->
        Text(text = vm.getTextTest(), modifier = Modifiers.scaffoldedContent(padding))
    }
}