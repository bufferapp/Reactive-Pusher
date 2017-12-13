package org.buffer.android.reactivepusher.model

data class TriggerEventError(val error: String) : Throwable(error)