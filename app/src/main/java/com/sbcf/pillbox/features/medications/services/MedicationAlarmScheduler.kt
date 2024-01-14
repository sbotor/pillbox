package com.sbcf.pillbox.features.medications.services

import com.sbcf.pillbox.features.medications.data.MedicationNotification

interface MedicationAlarmScheduler {
    suspend fun scheduleAll(notifications: List<MedicationNotification>)
}
