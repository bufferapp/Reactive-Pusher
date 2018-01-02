package org.buffer.android.reactivepusher.model

class ConnectionError(val error: String?, val code: String?, exception: Exception?)
    : Exception(exception)