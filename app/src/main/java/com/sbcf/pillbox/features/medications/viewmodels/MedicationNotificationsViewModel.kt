package com.sbcf.pillbox.features.medications.viewmodels

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbcf.pillbox.features.medications.data.MedicationNotification
import com.sbcf.pillbox.features.medications.data.repositories.MedicationNotificationsRepository
import com.sbcf.pillbox.features.medications.services.MedicationAlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationNotificationsViewModel @Inject constructor(
    private val repo: MedicationNotificationsRepository,
    private val scheduler: MedicationAlarmScheduler) :
    ViewModel() {
    // TODO: This flow is kind of wacky. Need to find a way to determine if the user denied the permission.
    // https://developer.android.com/develop/ui/views/notifications/notification-permission#user-select-dont-allow
    class NotificationPermissionState {
        var isGranted by mutableStateOf(false)
            private set
        var isEstablished by mutableStateOf(false)
            private set

        fun check(context: Context) {
            if (isEstablished) {
                return
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                isGranted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                isGranted = true
                isEstablished = true
            }
        }

        fun shouldRequest(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        }

        fun setRequestResult(value: Boolean) {
            isEstablished = true
            isGranted = value
        }
    }

    val notificationPermission = NotificationPermissionState()

    var notifications by mutableStateOf(emptyList<MedicationNotification>())
        private set

    fun fetchNotifications() {
        viewModelScope.launch {
            val fetched = repo.getAll()
            notifications = fetched
            // TODO: This currently schedules the mocked notifications
            scheduler.scheduleAll(fetched)
        }
    }
}