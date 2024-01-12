package com.sbcf.pillbox.features.medications.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medication(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val dosage: String
)