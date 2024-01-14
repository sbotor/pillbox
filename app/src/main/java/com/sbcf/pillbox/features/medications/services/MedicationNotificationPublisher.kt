package com.sbcf.pillbox.features.medications.services

import com.sbcf.pillbox.features.medications.data.MedicationNotification

interface MedicationNotificationPublisher {
    fun publish(notification: MedicationNotification)
}

