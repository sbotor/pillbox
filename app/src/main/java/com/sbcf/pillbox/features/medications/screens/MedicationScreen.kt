package com.sbcf.pillbox.features.medications.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medications.viewmodels.MedicationViewModel
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationScreen(
    onMedicationClick: () -> Unit,
    //onAlarmsClick: () -> Unit,
    vm: MedicationViewModel = hiltViewModel()
)
{
    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(title = { Text("Wybierz aktywność") })} //TODO
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scaffoldedContent(padding)
        ) {

            Button(
                onClick = onMedicationClick,
                modifier = Modifier
                    .size(width = 800.dp, height = 80.dp)
                    .padding(10.dp)
                ) {
                Text(
                    text = stringResource(id = R.string.medication),
                    fontSize = 30.sp)
            }

            Row {
                Button(
                    onClick = { /*onAlarmsClick*/ Toast.makeText(context, "Nie istnieje jeszcze", Toast.LENGTH_SHORT).show() },
                    modifier = Modifier
                        .size(width = 800.dp, height = 80.dp)
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.alarms),
                        fontSize = 30.sp)
                }
            }
        }
    }
}