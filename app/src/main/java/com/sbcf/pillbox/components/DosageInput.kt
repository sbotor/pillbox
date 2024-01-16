package com.sbcf.pillbox.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medications.models.Dosage
import com.sbcf.pillbox.features.medications.models.DosageUnit
import com.sbcf.pillbox.features.medications.models.TimeInterval
import kotlin.reflect.KProperty0

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DosageInput(
    dosage : Dosage,
    isEditable: KProperty0<Boolean>
){
    var unitIsExpanded by remember { mutableStateOf(false)}
    /*var amount by remember { mutableStateOf(dosage.amount.toString()) }*/

    var intervalIsExpanded by remember { mutableStateOf(false) }
    /*var interval by remember { mutableStateOf(dosage.interval.toString())}*/


    /*if(amount.isNotEmpty())
        amount = dosage.amount.toString()

    if(interval.isNotEmpty())
        interval = dosage.interval.toString()*/



    Row {
        /*TextField(
            value = amount,
            onValueChange = {
                if(it.length < 3)
                {
                    amount = it
                    if(amount.isNotEmpty())
                    {
                        dosage.amount = amount.trim().toInt()
                    }
                }
            },
            label = { Text( stringResource(id = R.string.amount)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .width(100.dp)
                .padding(10.dp),
            enabled = isEditable
        )*/

        NumberField(
            state = dosage::amount,
            isEditable = isEditable.get(),
            labelId = R.string.amount,
            maxLength = 2,
            modifier = Modifier
                .width(100.dp)
                .padding(10.dp)
        )
        ExposedDropdownMenuBox(
            expanded = unitIsExpanded,
            onExpandedChange = { newVal ->
                if(isEditable.get())
                    unitIsExpanded = newVal
            },
            modifier = Modifier
                .width(200.dp)
                .padding(10.dp)
        ) {
            TextField(
                value = dosage.unit.shortcut,
                readOnly = true,
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = unitIsExpanded)
                },
                modifier = Modifier.menuAnchor(),
            )

            ExposedDropdownMenu(
                expanded = unitIsExpanded,
                onDismissRequest = { unitIsExpanded = false }
            ) {
                for(value in DosageUnit.entries)
                {
                    DropdownMenuItem(
                        text = { Text(text = value.shortcut) },
                        onClick = {
                            dosage.unit = value
                            unitIsExpanded = false
                        }
                    )
                }
            }
        }
    }
    Row (modifier = Modifier.padding(20.dp)){
        Text(text = " co ")
    }
    Row{
        /*TextField(
            value = interval,
            onValueChange = {
                if(it.length < 4)
                {
                    interval = it
                    if(interval.isNotEmpty())
                    {
                        dosage.interval = interval.trim().toInt()
                    }
                }
            },
            label = { Text(stringResource(id = R.string.amount)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .width(100.dp)
                .padding(10.dp),
            enabled = isEditable
        )*/

        NumberField(
            state = dosage::interval,
            isEditable = isEditable.get(),
            labelId = R.string.amount,
            maxLength = 3,
            modifier = Modifier
                .width(100.dp)
                .padding(10.dp)
        )

        ExposedDropdownMenuBox(
            expanded = intervalIsExpanded,
            onExpandedChange = { it ->
                if(isEditable.get()) {
                    intervalIsExpanded = it
                }
            },
            modifier = Modifier
                .width(200.dp)
                .padding(10.dp),
        ) {
            TextField(
                value = dosage.intervalType.getName(dosage.interval),
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = intervalIsExpanded)
                },
                modifier = Modifier.menuAnchor(),
            )

            ExposedDropdownMenu(
                expanded = intervalIsExpanded,
                onDismissRequest = { intervalIsExpanded = false }
            ) {
                for (value in TimeInterval.entries) {
                    DropdownMenuItem(
                        text = { Text(text = value.getName(dosage.interval)) },
                        onClick = {
                            dosage.intervalType = value
                            intervalIsExpanded = false
                        }
                    )
                }
            }
        }
    }
}