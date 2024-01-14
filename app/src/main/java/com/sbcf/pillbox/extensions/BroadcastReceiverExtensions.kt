package com.sbcf.pillbox.extensions

import android.content.BroadcastReceiver
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun BroadcastReceiver.launchGlobalAsync(block: suspend () -> Unit) {
    val pendingResult = goAsync()
    GlobalScope.launch {
        try {
            block()
        } finally {
            pendingResult.finish()
        }
    }
}