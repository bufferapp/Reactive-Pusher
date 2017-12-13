package org.buffer.android.reactivepusher.model

data class ChannelError(val error: String?) : Exception(error)